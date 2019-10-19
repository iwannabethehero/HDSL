package com.hanlzz.findqr.step;

import com.hanlzz.findqr.common.IStep;
import com.hanlzz.findqr.common.StepResult;

import java.awt.image.BufferedImage;
import java.util.Map;

import static com.hanlzz.findqr.Main.DE_NOISE;

/**
 * 降噪去白点
 *
 * @author liets
 */
public class DeNoiseStep implements IStep {

    private static final int WRITE = 0xffffffff;
    private static final int BLACK = 0xff000000;

    @Override
    public StepResult run(Map<String, Object> context) {
        context.put("key_1",(Integer)context.getOrDefault("key_1",0)+1);
        BufferedImage image = (BufferedImage) context.get("image");
        int wid = image.getWidth();
        int hei = image.getHeight();
        for (int i = 0; i < wid; i++) {
            for (int j = 0; j < hei; j++) {
                int rgb = image.getRGB(i, j);
                if (rgb == WRITE) {
                    int c = 0;
                    if (overFlow(i - 1, j - 1, wid, hei)) {
                        c += image.getRGB(i - 1, j - 1) & 1;
                    }
                    if (overFlow(i, j - 1, wid, hei)) {
                        c += image.getRGB(i, j - 1) & 1;
                    }
                    if (overFlow(i + 1, j - 1, wid, hei)) {
                        c += image.getRGB(i + 1, j - 1) & 1;
                    }
                    if (overFlow(i - 1, j, wid, hei)) {
                        c += image.getRGB(i - 1, j) & 1;
                    }
                    if (overFlow(i + 1, j, wid, hei)) {
                        c += image.getRGB(i + 1, j) & 1;
                    }
                    if (overFlow(i - 1, j + 1, wid, hei)) {
                        c += image.getRGB(i - 1, j + 1) & 1;
                    }
                    if (overFlow(i, j + 1, wid, hei)) {
                        c += image.getRGB(i, j + 1) & 1;
                    }
                    if (overFlow(i + 1, j + 1, wid, hei)) {
                        c += image.getRGB(i + 1, j + 1) & 1;
                    }

                    if (c < 4){
                        image.setRGB(i,j,BLACK);
                    }
                }
            }
        }
        if ((Integer)context.get("key_1")==DE_NOISE){
            return StepResult.breakLoop();
        }
        return null;
    }

    private boolean overFlow(int x, int y, int x1, int y1) {
        return !(x < 0 || y < 0 || x == x1 || y == y1);
    }

    @Override
    public boolean ignoreProxy() {
        return true;
    }
}
