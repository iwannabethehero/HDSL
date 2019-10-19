package com.hanlzz.findqr.proxy;

import com.hanlzz.findqr.common.IStep;
import com.hanlzz.findqr.common.InitStepImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 生成代理
 *
 * @author hlz
 */
public class ProxyBuilder {

    public static IStep getProxyInstance(IStep step, IHandler proxy) {
        if (step == null || proxy == null || step.ignoreProxy() || step instanceof InitStepImpl) {
            return step;
        }
        return (IStep) Proxy.newProxyInstance(step.getClass().getClassLoader(), step.getClass().getInterfaces(), proxy);
    }
}
