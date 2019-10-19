package com.hanlzz.findqr.test;

import com.hanlzz.findqr.common.IStep;
import com.hanlzz.findqr.common.StepResult;

import java.util.Map;


public class Step3 implements IStep {
    @Override
    public StepResult run(Map<String, Object> context) {
        System.out.println("Step3");
        return StepResult.continueFlow("A");
    }

    @Override
    public boolean ignoreProxy() {
        return false;
    }
}
