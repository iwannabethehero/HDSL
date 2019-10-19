package com.hanlzz.findqr.step;

import com.hanlzz.findqr.common.IStep;
import com.hanlzz.findqr.common.StepResult;

import java.awt.image.BufferedImage;
import java.util.Map;

public class BinaryStep implements IStep {

    private static final int WRITE = 0xffffffff;
    private static final int BLACK = 0xff000000;

    @Override
    public StepResult run(Map<String, Object> context) {
        BufferedImage image = (BufferedImage)context.get("image");
        int gr = (Integer)context.getOrDefault("special_gray", 80);

        context.put("image",image);

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int gray = image.getRGB(i, j) & 0xff;
                if (gray > gr){
                    //白色
                    image.setRGB(i, j, BLACK);
                }else{
                    //黑色
                    image.setRGB(i, j, WRITE);
                }
            }
        }
        return StepResult.continueFlow();
    }

    @Override
    public boolean ignoreProxy() {
        return true;
    }
}
