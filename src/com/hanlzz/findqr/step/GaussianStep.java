package com.hanlzz.findqr.step;

import com.hanlzz.findqr.common.IStep;
import com.hanlzz.findqr.common.StepResult;

import java.awt.image.BufferedImage;
import java.util.Map;

import static com.hanlzz.findqr.ColorUtils.*;

public class GaussianStep implements IStep {
    private final static double SIGMA = 0.5;
    private final static int AREA = 1;

    private static double[][] WEIGHT = initWeight(SIGMA, AREA);


    @Override
    public StepResult run(Map<String, Object> context) {
        BufferedImage image = (BufferedImage) context.get("image");
        BufferedImage image2 = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int i = AREA; i < image.getWidth() - AREA; i++) {
            for (int j = AREA; j < image.getHeight() - AREA; j++) {
                int gray = getGaussianColor(i, j, image, AREA, WEIGHT);
                image2.setRGB(i, j, color2Rgb(0xff, gray, gray, gray));
            }
        }
        context.put("image",image2);
        return null;
    }

    private int getGaussianColor(int i, int j, BufferedImage image, int area, double[][] weight) {
        int ans = 0;
        for (int k = 0; k <= 2*area; k++) {
            for (int l = 0; l <= 2*area; l++) {
                ans += weight[k][l] * (image.getRGB(i - area + k, j - area + l) & 0xff);
            }
        }
        return ans;
    }

    @Override
    public boolean ignoreProxy() {
        return true;
    }
}
