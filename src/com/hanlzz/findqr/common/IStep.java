package com.hanlzz.findqr.common;

import com.hanlzz.findqr.flow.FlowStats;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liets
 */
public interface IStep {
    /**
     * 执行步骤的方法,返回值FlowStats中的设置
     * 1.8改为返回枚举
     * @param context
     * @return
     */
    StepResult run(Map<String,Object> context);

    /**
     * 1.8之后default方法默认为false
     * @return 是否忽略代理设置
     */
    boolean ignoreProxy();
}
