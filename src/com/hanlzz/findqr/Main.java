package com.hanlzz.findqr;

import com.hanlzz.findqr.common.StepNode;
import com.hanlzz.findqr.flow.Flow;
import com.hanlzz.findqr.flow.FlowBuilder;
import com.hanlzz.findqr.flow.FlowRunner;
import com.hanlzz.findqr.handler.StepImageMonitorHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

public class Main {

    /**
     * 度数阈值,与选定区域有关
     */
    public static final int APEX = 40;

    /**
     * 权重长度,与选定区域有关
     */
    public static final int LENGTH = 40;

    /**
     * 截取图像边框宽度
     */
    public static final int FRAME = 110;

    /**
     * 降噪算法次数
     */
    public static final int DE_NOISE = 3;

    /**
     * 填充比例
     */
    public static final double RATE = 0.14;


    /**
     * 最高填充比
     */
    public static final double RATE_MAX = 0.7;

    /**
     * 二值化灰度阈值
     */
    private static final int GRAY = 160;


    private static final int GRAY_SP = 80;


    /**
     * 是否打印横纵数组
     */
    public static final boolean PRINT_ARR = false;
    //3,4,8,20,22,24-32,36,38,40
    
    public static void main(String[] args) throws Exception {
//        StepNode stepNode = FlowBuilder.buildFlow(Flow.FIND_QR, null);
        StepNode stepNode = FlowBuilder.buildFlow(Flow.DISCERN, null);
//        StepNode stepNode = FlowBuilder.buildFlow(Flow.DEMO_FLOW, StepImageMonitorHandler.class);
//
        int fail = 1;
        for (int i = 1; i < 47; i++) {
            HashMap<String, Object> map = new HashMap<>();
            BufferedImage bufferedImage
                    = ImageIO.read(new File("C:\\Users\\liets\\Desktop\\test\\input\\"+i+".jpg"));
            //4,7,8,14,19,21,22,24,25,26,27,31,32,36,37,38
            map.put("image",bufferedImage);
            map.put("gray",GRAY);
            map.put("special_gray",GRAY_SP);
            Object o = FlowRunner.runFlow(stepNode, map);
            System.out.println("第 "+i+" 张图片解析结果 : "+(o==null ? "解析失败 ! "+fail++:o.toString()));
        }

    }
}
