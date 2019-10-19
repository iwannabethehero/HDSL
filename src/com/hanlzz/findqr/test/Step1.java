package com.hanlzz.findqr.test;

import com.hanlzz.findqr.common.IStep;
import com.hanlzz.findqr.common.StepResult;

import java.util.Map;


public class Step1 implements IStep {
    @Override
    public StepResult run(Map<String, Object> context) {
        System.out.println("Step1");
        return null;
    }

    @Override
    public boolean ignoreProxy() {
        return false;
    }
}
