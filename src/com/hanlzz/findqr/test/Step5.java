package com.hanlzz.findqr.test;

import com.hanlzz.findqr.common.IStep;
import com.hanlzz.findqr.common.StepResult;

import java.util.Map;


public class Step5 implements IStep {
    @Override
    public StepResult run(Map<String, Object> context) {
        System.out.println("Step5");
        return StepResult.continueFlow();
    }

    @Override
    public boolean ignoreProxy() {
        return false;
    }
}
