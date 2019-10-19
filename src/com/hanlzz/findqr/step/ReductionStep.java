package com.hanlzz.findqr.step;

import com.hanlzz.findqr.common.IStep;
import com.hanlzz.findqr.common.StepResult;

import java.awt.image.BufferedImage;
import java.util.Map;

public class ReductionStep implements IStep {
    @Override
    public StepResult run(Map<String, Object> context) {

        BufferedImage image = (BufferedImage) context.get("grayImage");
        BufferedImage image2 = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        image2.setData(image.getData());
        context.put("image", image2);
        return StepResult.continueFlow();
    }

    @Override
    public boolean ignoreProxy() {
        return false;
    }
}
