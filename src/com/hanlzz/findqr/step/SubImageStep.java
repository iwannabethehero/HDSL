package com.hanlzz.findqr.step;

import com.hanlzz.findqr.common.IStep;
import com.hanlzz.findqr.common.StepResult;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;

import static com.hanlzz.findqr.Main.*;

/**
 * 图像裁剪
 *
 * @author liets
 */
public class SubImageStep implements IStep {
    @Override
    public StepResult run(Map<String, Object> context) {


        BufferedImage image = (BufferedImage) context.get("image");
        BufferedImage gray = (BufferedImage) context.get("grayImage");
        ArrayList boundWid = (ArrayList) context.get("boundWid");
        ArrayList boundHei = (ArrayList) context.get("boundHei");
        for (Object wi : boundWid) {
            Integer[] wid = (Integer[]) wi;
            for (Object he : boundHei) {
                Integer[] hei = (Integer[]) he;
                if (checkIntersection(image, wid, hei)) {
                    wid[0] = wid[0] > FRAME ? wid[0] - FRAME : 0;
                    wid[1] = wid[1] < image.getWidth() - FRAME ? wid[1] + FRAME : image.getWidth() - 1;

                    hei[0] = hei[0] > FRAME ? hei[0] - FRAME : 0;
                    hei[1] = hei[1] < image.getHeight() - FRAME ? hei[1] + FRAME : image.getHeight() - 1;
//                    System.out.println(wid[0]+"-"+wid[1]+"  ");

//                    System.out.println(gray.getWidth() +"  xxxx yyyy  "+gray.getHeight() );
                    context.put("image", gray.getSubimage(wid[0], hei[0], wid[1] - wid[0] + 1, hei[1] - hei[0] + 1));

                    context.put("gray",0xff>>1);
                    return null;
                }
            }
        }

        return null;
    }

    private boolean checkIntersection(BufferedImage image, Integer[] wid, Integer[] hei) {
        int sum = 0;
        for (int i = wid[0]; i < wid[1]; i++) {
            for (int j = hei[0]; j < hei[1]; j++) {
                sum += image.getRGB(i, j) & 1;
            }
        }
        double rate = (double) sum/((wid[1]-wid[0])*(hei[1]-hei[0]));
//        System.out.println("x:"+wid[0]+'-'+wid[1]+"  y:"+hei[0]+'-'+hei[1]+"   ==========   "+rate);
        return rate > RATE && rate < RATE_MAX;
    }

    @Override
    public boolean ignoreProxy() {
        return false;
    }
}
