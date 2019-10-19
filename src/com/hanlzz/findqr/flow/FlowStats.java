package com.hanlzz.findqr.flow;

/**
 * flow状态控制
 * @author liets 
 */
public enum  FlowStats {


    /**
     * 流程结束(所有流程)
     */
    END,
    /**
     * 流程继续(默认)
     */
    CONTINUE,
    /**
     * 循环继续
     */
    CONTINUE_LOOP,
    /**
     * 错误,流程停止,抛出异常
     */
    ERROR,
    /**
     * 跳出当前循环
     */
    BREAK_LOOP,
    /**
     * 流程停止,抛出业务异常
     */
    BUSINESS_EXCEPTION,
    /**
     * 结束当前流程,只用于内部
     */
    BATCH_END;


}
