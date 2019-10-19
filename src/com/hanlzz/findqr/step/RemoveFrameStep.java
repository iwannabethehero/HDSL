package com.hanlzz.findqr.step;

import com.hanlzz.findqr.common.IStep;
import com.hanlzz.findqr.common.StepResult;

import java.awt.image.BufferedImage;
import java.util.Map;

public class RemoveFrameStep implements IStep {
    @Override
    public StepResult run(Map<String, Object> context) {
        BufferedImage image = (BufferedImage) context.get("image");
        int widFrame = image.getWidth() / 32;
        int heiFrame = image.getHeight() / 32;
        for (int i = 1; i < widFrame; i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                image.setRGB(i, j, 0xff000000);
                image.setRGB(image.getWidth() - i, j, 0xff000000);
            }
        }

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 1; j < heiFrame; j++) {
                image.setRGB(i, j, 0xff000000);
                image.setRGB(i, image.getHeight() - j, 0xff000000);
            }
        }
        return null;
    }

    @Override
    public boolean ignoreProxy() {
        return true;
    }
}
