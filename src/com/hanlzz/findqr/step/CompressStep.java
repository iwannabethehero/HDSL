package com.hanlzz.findqr.step;

import com.hanlzz.findqr.common.IStep;
import com.hanlzz.findqr.common.StepResult;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * 图像压缩
 * @author liets
 */
public class CompressStep implements IStep {
    @Override
    public StepResult run(Map<String, Object> context) {
        BufferedImage src = (BufferedImage) context.get("image");
        int width = src.getWidth();
        int height = src.getHeight();
        int max = Math.max(width, height);
        if (max>3000){
            double rate = 3000.0/Math.max(width,height);
            BufferedImage tag = new BufferedImage((int) (width*rate), (int) (height*rate), BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(src.getScaledInstance((int) (width*rate), (int) (height*rate), Image.SCALE_SMOOTH), 0, 0, null);
            context.put("image",tag);
        }
        return null;
    }

    @Override
    public boolean ignoreProxy() {
        return true;
    }
}
