package com.hanlzz.findqr.flow;

import com.hanlzz.findqr.common.IStep;
import com.hanlzz.findqr.common.StepNode;
import com.hanlzz.findqr.common.StepResult;

import java.util.Map;

import static com.hanlzz.findqr.flow.FlowBuilder.LOOP;
import static com.hanlzz.findqr.flow.FlowStats.*;

/**
 * 该类作为flow的执行器
 * 通过dfs遍历树节点可以很好的对遍历过程进行剪枝和延申
 * 在规定区域增加事务控制
 *
 * @author liets
 */
public class FlowRunner {

    public static Object runFlow(StepNode head, Map<String, Object> param) {
        if (head == null) {
            return null;
        }
        FlowStats stats = executeFlow(head, param);
        if (stats != FlowStats.END && stats != FlowStats.BATCH_END) {
            throw new RuntimeException("flow 执行未正常结束!");
        }
        return param.get("result");
    }


    private static FlowStats executeFlow(StepNode head, Map<String, Object> param) {
        if (head == null) {
            return null;
        }
        IStep step = head.getStep();
        StepResult run = StepResult.continueFlow();
        //先运行step
        if (step != null) {
            run = (run = step.run(param)) == null ? StepResult.continueFlow() : run;
            param.put("flow_batch", run.getBatch());
        }
        switch (run.getStats()) {
            //TODO log
            case ERROR:
                handleError(param);
                throw new RuntimeException("执行中flow时出现错误,搜索错误原因 : [" + run.getMsg() + "] 定位问题");
            case CONTINUE_LOOP:
            case BREAK_LOOP:
            case END:
                return run.getStats();
            case BUSINESS_EXCEPTION:
                handleBusinessException(param);
            default:
        }

        //判断是否存在branch
        if (head.getBranch() == null || head.getBranch().length == 0) {
            //TODO log
        } else {
            if (head.getBranchName() == null || head.getBranchName().length == 0) {
                throw new RuntimeException("流程初始化错误!");
            }
            if (LOOP.equals(head.getBranchName()[0])) {
                //如果是loop
                loop:
                for (int i = 0; i < 3; i++) {
                    FlowStats stats = executeFlow(head.getBranch()[0], param);
                    switch (stats) {
                        case END:
                            return END;
                        case CONTINUE_LOOP:
                            continue loop;
                        case BREAK_LOOP:
                            break loop;
                        case BUSINESS_EXCEPTION:
                            handleBusinessException(param);
                        default:
                    }
                }
            } else {
                String bat = (String) param.get("flow_batch");
                for (int i = 0; i <head.getBranchName().length ; i++) {
                    String name = head.getBranchName()[i];
                    if(name != null && name.equals(bat)){
                        FlowStats stats = executeFlow(head.getBranch()[i], param);
                        switch (stats){
                            case END:
                            case CONTINUE_LOOP:
                            case BREAK_LOOP:
                                return stats;
                            case BUSINESS_EXCEPTION:
                                handleBusinessException(param);
                            default:
                        }
                    }
                }
            }
        }
        //执行next
        return head.getNext() == null ? BATCH_END : executeFlow(head.getNext(), param);
    }

    private static void handleBusinessException(Map<String, Object> param) {
    }

    private static void handleError(Map<String, Object> param) {
    }
}
