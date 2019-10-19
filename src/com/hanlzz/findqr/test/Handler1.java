package com.hanlzz.findqr.test;

import com.hanlzz.findqr.common.IStep;
import com.hanlzz.findqr.proxy.IHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Handler1  implements IHandler {
    private IStep step;
    Handler1(IStep step){
        this.step = step;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)  {
        System.out.println("代理开始!!!");
        if ("run".equals(method.getName())) {
            try {
                return method.invoke(step, args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
