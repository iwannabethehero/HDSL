package com.hanlzz.findqr.flow;

import com.hanlzz.findqr.common.IStep;
import com.hanlzz.findqr.common.StepNode;
import com.hanlzz.findqr.proxy.IHandler;
import com.hanlzz.findqr.proxy.ProxyBuilder;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static com.hanlzz.findqr.flow.Flow.FLOW_POOL;
import static com.hanlzz.findqr.flow.Flow.STEPS;

/**
 * 流程构建器
 *
 * @author liets
 */
public class FlowBuilder {


    private static final String REX = "[^\\[\\]{}()]";
    static final String LOOP = "loop";
    private static final String MAP = "map";
    private static final String ATOM = "atom";

    public static StepNode buildFlow(Flow flow, Class<? extends IHandler> proxyClazz) {
        if (flow == null) {
            throw new RuntimeException("flow can not be empty!");
        }
        Map<Class<? extends IHandler>, StepNode> rec = FLOW_POOL.get(flow);
        StepNode head;
        //lazy and singleton
        if (rec == null || rec.get(proxyClazz) == null) {
            String ans = checkFlow(flow.flows);
            head = new StepNode();
            try {
                build(ans, head, proxyClazz);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
            Map<Class<? extends IHandler>, StepNode> va = FLOW_POOL.getOrDefault(flow, new HashMap<>());
            va.put(proxyClazz, head);
            FLOW_POOL.put(flow, va);
        } else {
            head = rec.get(proxyClazz);
        }
        return head;
    }


    // A >> B >> C
    //  map[A:xxx,B:xxx] >> C
    // loop(A >> B) >> C
    // A >> loop(B)
    // loop(map[A:xxx] >> B)
    // map(A:loop(B >> C),D : F>>map[C : xxx])


    /**
     * 递归加载,好理解一些
     *
     * @param ans  flow
     * @param head h
     */
    private static void build(String ans, StepNode head, Class<? extends IHandler> proxyClazz) {
        if (ans == null || ans.length() == 0) {
            return;
        }
        while ('>' == ans.charAt(0)) {
            if (ans.length() < 3) {
                return;
            }
            ans = ans.substring(2);
        }
        if (ans.startsWith(MAP)) {
            buildBranch(ans, head, proxyClazz);
        } else if (ans.startsWith(LOOP)) {
            buildLoop(ans, head, proxyClazz);
        } else if (ans.startsWith(ATOM)) {
            //怎么加事务,值得考虑,在这里留一个拓展口
            throw new UnsupportedOperationException("不支持的操作 : atom");
        } else {
            buildCommon(ans, head, proxyClazz);
        }
    }

    /**
     * 检测下一个括号
     */
    private static int endIdx(String str, int stIdx, char end) {

        int count = 1;
        char st = str.charAt(stIdx);
        for (int i = stIdx + 1; i < str.length(); i++) {
            if (str.charAt(i) == st) {
                count++;
            } else if (str.charAt(i) == end) {
                count--;
            }
            if (count == 0) {
                return i;
            }
        }
        throw new RuntimeException("流程括号不符合规范!");
    }


    /**
     * 该方法中无须做校验
     *
     * @param flow       处理后的流程
     * @param proxyClazz 代理类
     */
    private static void buildCommon(String flow, StepNode head, Class<? extends IHandler> proxyClazz) {
        int idx = flow.indexOf('>');
        if (idx == -1) {
            idx = flow.length();
        }
        IStep iStep = STEPS.get(flow.substring(0, idx));
        if (iStep == null) {
            throw new RuntimeException("flow解析失败! 无法找到 [" + flow.substring(0, idx) + "] 对应step");
        }

        //如果添加了代理,则加载代理
        if (proxyClazz != null) {
            try {
                Constructor<? extends IHandler> constructor = proxyClazz.getDeclaredConstructor(IStep.class);
                constructor.setAccessible(true);
                IHandler handler = constructor.newInstance(iStep);
                iStep = ProxyBuilder.getProxyInstance(iStep, handler);
            } catch (Exception e) {
                //日志 TODO
                e.printStackTrace();
            }
        }

        head.setNext(new StepNode(iStep));
        if ((idx += 2) < flow.length()) {
            build(flow.substring(idx), head.getNext(), proxyClazz);
        }
    }

    private static void buildBranch(String flow, StepNode head, Class<? extends IHandler> proxyClazz) {
        //map[A:xxx]>>map[b:]
        int i = endIdx(flow, 3, ']');
        String f = flow.substring(4, i);
        //build batch
        //应该找出所有度为0的冒号和逗号
//        String[] ss = f.split("[:,]");
        String[] ss = splitDegrees0(f);
        int len = ss.length >> 1;
        //build batch
        StepNode[] bat = new StepNode[len];
        String[] batName = new String[len];

        for (int j = 0, p = 0; j < len; j++) {
            batName[j] = ss[p++];
            StepNode emp = new StepNode();
            bat[j] = emp;
            build(ss[p++], emp, proxyClazz);
        }

        head.setBranchAndName(batName, bat);

        //build empty
        StepNode empty = new StepNode();
        head.setNext(empty);
        if (i < flow.length() - 1) {
            build(flow.substring(i + 1), empty, proxyClazz);
        }
    }

    private static String[] splitDegrees0(String f) {
        char[] cs = f.toCharArray();
        int deg = 0, pre = 0;
        ArrayList<String> ans = new ArrayList<>();
        for (int i = 0; i < cs.length; i++) {
            if (cs[i] == '[') {
                deg++;
            } else if (cs[i] == ']') {
                deg--;
            } else if (deg == 0 && (cs[i] == ':' || cs[i] == ',')) {
                ans.add(new String(cs, pre, i - pre));
                pre = i + 1;
            }
        }
        ans.add(f.substring(pre));
        return ans.toArray(new String[0]);
    }

    private static void buildLoop(String flow, StepNode head, Class<? extends IHandler> proxyClazz) {
        int i = endIdx(flow, 4, ']');
        String f = flow.substring(5, i);
        StepNode emp = new StepNode();
        head.setBranchAndName(new String[]{LOOP}, new StepNode[]{emp});

        build(f, emp, proxyClazz);

        //build empty
        StepNode empty = new StepNode();
        head.setNext(empty);
        if (i < flow.length() - 1) {
            build(flow.substring(i + 1), empty, proxyClazz);
        }
    }

    /**
     * 检查括号
     * 检查流程箭头
     *
     * @param flows 流程
     * @return res
     */
    private static String checkFlow(String flows) {
        String s = flows.replaceAll("[^\\u4e00-\\u9fa5_a-zA-Z0-9\\[\\](){},>:]", "");
        //检测流程符号
        boolean f = false;
        for (int i = 0; i < s.length(); i++) {
            if (f) {
                if (s.charAt(i) == '>') {
                    f = false;
                } else {
                    throw new RuntimeException("flow定义不符合规范!   :  '>' ");
                }
            } else if (s.charAt(i) == '>') {
                f = true;
            }
        }

        if (isInValid(s.replaceAll(REX, ""))) {
            throw new RuntimeException("flow定义不符合规范!  : 括号");
        }
        return s;
    }


    private static boolean isInValid(String s) {
        Stack<Character> stack = new Stack<>();
        char[] chars = s.toCharArray();
        for (char aChar : chars) {
            if (stack.size() == 0) {
                stack.push(aChar);
            } else if (isSym(stack.peek(), aChar)) {
                stack.pop();
            } else {
                stack.push(aChar);
            }
        }
        return stack.size() != 0;
    }

    private static boolean isSym(char c1, char c2) {
        return (c1 == '(' && c2 == ')') || (c1 == '[' && c2 == ']') || (c1 == '{' && c2 == '}');
    }


}
