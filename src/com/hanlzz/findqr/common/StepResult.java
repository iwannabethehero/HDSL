package com.hanlzz.findqr.common;

import com.hanlzz.findqr.flow.FlowStats;

/**
 * 包装返回流程信息
 * @author liets
 */
public final class StepResult {

    private FlowStats stats;

    private String batch;

    private String msg;


    /**
     * 构造器私有,必须使用静态方法进行构造
     */
    private StepResult(){}
    public static StepResult endFlow(){
        StepResult ans = new StepResult();
        ans.stats = FlowStats.END;
        return ans;
    }
    /**
     * 正常操作如果在下个流程中有分支判断,则需要使用
     * method(String batch) 有参方法指定对应的分支名
     * @return step结果
     */
    public static StepResult continueFlow(){
        return continueFlow(null);
    }

    public static StepResult continueFlow(String batch){
        StepResult ans = new StepResult();
        ans.stats = FlowStats.CONTINUE;
        ans.batch = batch;
        return ans;
    }

    public static StepResult continueLoop(){
        return continueLoop(null);
    }

    public static StepResult continueLoop(String batch) {
        StepResult ans = new StepResult();
        ans.stats = FlowStats.CONTINUE_LOOP;
        ans.batch = batch;
        return ans;
    }

    public static StepResult breakLoop(){
        return breakLoop(null);
    }

    public static StepResult breakLoop(String batch) {
        StepResult ans = new StepResult();
        ans.stats = FlowStats.BREAK_LOOP;
        ans.batch = batch;
        return ans;
    }


    /**
     * 出现错误时必须指定错误原因,执行时会打印日志
     * @param msg 错误原因
     * @return step result
     */
    public static StepResult returnError(String msg) {
        StepResult ans = new StepResult();
        ans.stats = FlowStats.ERROR;
        ans.msg = msg;
        return ans;
    }


    public FlowStats getStats() {
        return stats;
    }

    public String getBatch() {
        return batch;
    }

    public String getMsg() {
        return msg;
    }
}
