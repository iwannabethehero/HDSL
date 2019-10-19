package com.hanlzz.findqr.test;

import com.hanlzz.findqr.common.IStep;
import com.hanlzz.findqr.common.StepResult;

import java.util.Map;


public class Step6 implements IStep {
    @Override
    public StepResult run(Map<String, Object> context) {
        System.out.println("Step6");
        return null;
    }

    @Override
    public boolean ignoreProxy() {
        return false;
    }
}
