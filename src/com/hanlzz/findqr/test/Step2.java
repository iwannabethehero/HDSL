package com.hanlzz.findqr.test;

import com.hanlzz.findqr.common.IStep;
import com.hanlzz.findqr.common.StepResult;

import java.util.Map;


public class Step2 implements IStep {
    @Override
    public StepResult run(Map<String, Object> context) {
        System.out.println("Step2");
        return StepResult.continueFlow("B");
    }

    @Override
    public boolean ignoreProxy() {
        return true;
    }
}
