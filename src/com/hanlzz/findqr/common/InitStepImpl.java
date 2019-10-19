package com.hanlzz.findqr.common;

import com.hanlzz.findqr.flow.FlowStats;

import java.util.Map;

/**
 * @author liets
 */
public class InitStepImpl implements IStep {
    @Override
    public StepResult run(Map<String, Object> context) {
        return null;
    }

    @Override
    public boolean ignoreProxy() {
        return true;
    }
}
