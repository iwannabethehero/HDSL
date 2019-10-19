package com.hanlzz.findqr.step;

import com.hanlzz.findqr.common.IStep;
import com.hanlzz.findqr.common.MyQRCodeImage;
import com.hanlzz.findqr.common.StepResult;
import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.exception.DecodingFailedException;

import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author liets
 */
public class DiscernQRStep implements IStep {
    @Override
    public StepResult run(Map<String, Object> context) {
        BufferedImage image = (BufferedImage) context.get("image");

        QRCodeDecoder codeDecoder = new QRCodeDecoder();
        String result = null;
        try {
            result = new String(codeDecoder.decode(new MyQRCodeImage(image)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            context.put("result","解析失败");
            return null;
        }
        if (result.contains("AR1")){
            context.put("result",result);
            return StepResult.endFlow();
        }
        return StepResult.continueFlow();
    }

    @Override
    public boolean ignoreProxy() {
        return true;
    }
}
