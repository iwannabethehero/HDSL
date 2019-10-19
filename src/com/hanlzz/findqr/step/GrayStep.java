package com.hanlzz.findqr.step;

import com.hanlzz.findqr.common.IStep;
import com.hanlzz.findqr.common.StepResult;

import java.awt.image.BufferedImage;
import java.util.Map;

import static com.hanlzz.findqr.ColorUtils.color2Rgb;

public class GrayStep implements IStep {
    @Override
    public StepResult run(Map<String, Object> context) {

        BufferedImage image = (BufferedImage)context.get("image");
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                final int color = image.getRGB(i, j);
                final int r = (color >> 16) & 0xff;
                final int g = (color >> 8) & 0xff;
                final int b = color & 0xff;
                int gray = (int) (0.3 * r + 0.59 * g + 0.11 * b);
                int newPixel = color2Rgb(255, gray, gray, gray);
                image.setRGB(i, j, newPixel);
            }
        }

        BufferedImage bimage2 = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        bimage2.setData(image.getData());
        context.put("grayImage",bimage2);
        return StepResult.continueFlow();
    }



    @Override
    public boolean ignoreProxy() {
        return true;
    }
}
