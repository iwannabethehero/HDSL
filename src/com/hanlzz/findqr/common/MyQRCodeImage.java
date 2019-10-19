package com.hanlzz.findqr.common;

import jp.sourceforge.qrcode.data.QRCodeImage;

import java.awt.image.BufferedImage;

/**
 * @author liets
 */
public class MyQRCodeImage implements QRCodeImage {

    private BufferedImage image;

    public MyQRCodeImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public int getHeight() {
        return image.getHeight();
    }

    @Override
    public int getPixel(int i, int j) {
        return image.getRGB(i, j);
    }
}
