package com.hanlzz.findqr.step;

import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.hanlzz.findqr.common.BufferedImageLuminanceSource;
import com.hanlzz.findqr.common.IStep;
import com.hanlzz.findqr.common.StepResult;

import java.awt.image.BufferedImage;
import java.util.Hashtable;
import java.util.Map;

public class DiscernStep implements IStep {
    @Override
    public StepResult run(Map<String, Object> context) {
        MultiFormatReader formatReader = new MultiFormatReader();
        BufferedImage image = (BufferedImage)context.get("image");

        LuminanceSource source = new BufferedImageLuminanceSource(image);

        Binarizer binarizer = new HybridBinarizer(source);

        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);

        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");


        Result result = null;
        try {
            result = formatReader.decode(binaryBitmap,hints);
        } catch (NotFoundException e) {
            return StepResult.continueFlow();
        }

        context.put("result",result.getText());
        if (result.getText().contains("FFFFF")){
            return StepResult.endFlow();
        }
        return StepResult.continueFlow();
    }

    @Override
    public boolean ignoreProxy() {
        return true;
    }
}
