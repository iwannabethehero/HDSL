package com.hanlzz.findqr.step;

import com.hanlzz.findqr.common.IStep;
import com.hanlzz.findqr.common.StepResult;

import java.awt.image.BufferedImage;
import java.util.Map;

import static com.hanlzz.findqr.Main.APEX;

/**
 * 维度转换
 *
 * @author liets
 */
public class TranDimStep implements IStep {
    @Override
    public StepResult run(Map<String, Object> context) {
        BufferedImage image = (BufferedImage) context.get("image");
        int[] wid = new int[image.getWidth()];
        int[] hei = new int[image.getHeight()];
        for (int i = 0; i < image.getWidth(); i++) {
            int sum = 0;
            for (int j = 0; j < image.getHeight(); j++) {
                sum += image.getRGB(i, j) & 1;
                hei[j] += image.getRGB(i, j) & 1;
                if (i == image.getWidth() - 1) {
                    hei[j] = hei[j] > APEX ? 1 : 0;
                }
            }
            wid[i] = sum > APEX ? 1 : 0;
        }
        context.put("wids", wid);
        context.put("heis", hei);

        return StepResult.continueFlow();
    }

    @Override
    public boolean ignoreProxy() {
        return true;
    }
}
