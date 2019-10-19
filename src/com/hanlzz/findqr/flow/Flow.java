package com.hanlzz.findqr.flow;

import com.hanlzz.findqr.common.IStep;
import com.hanlzz.findqr.common.StepNode;
import com.hanlzz.findqr.proxy.IHandler;
import com.hanlzz.findqr.step.*;
import com.hanlzz.findqr.test.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义DSL
 * 根据配置的flow顺序执行对应类的run方法
 *
 * @author liets
 */
public enum Flow {

    /**
     * 模板流程
     */
    DEMO_FLOW("模板步骤1 >> 模板步骤2 >> map[ " +
            "B: 模板步骤3 >> map[" +
            "A:loop[map [ " +
            "A : 模板步骤3 >> 模板步骤6 >> 模板步骤2," +
            "B : 模板步骤4 ]]," +
            "B:模板步骤5]," +
            "A:模板步骤6] >> 模板步骤5"),


    FIND_QR_SIMPLE("图像识别"),
    //    FIND_QR("图像压缩 >> 图像灰度化 >> 图像识别 >> 图像二值化  >> loop[图片降噪] >> 去除边框 >> 维度转换 >> 一维数组定位 >> 图像裁剪 ");
    FIND_QR(" 图像压缩>> 图像灰度化 >> 图像二值化 >> loop[图片降噪] " +
            ">> 去除边框 >> 维度转换 >> 一维数组定位 >> 图像裁剪"),

    DISCERN("图像压缩 >> 图像灰度化 >>  图像识别 >> 高斯模糊 >> 图像识别 >> "
    +
            "还原图像 >> 图像二值化  >> loop[图片降噪] >> 去除边框 >> 维度转换 >> 一维数组定位 >> 图像裁剪 >> 图像识别>>" +
            "loop[高斯模糊>>图像识别]");


    public final static Map<String, IStep> STEPS = new HashMap<>();

    static {
        STEPS.put("模板步骤1", new Step1());
        STEPS.put("模板步骤2", new Step2());
        STEPS.put("模板步骤3", new Step3());
        STEPS.put("模板步骤4", new Step4());
        STEPS.put("模板步骤5", new Step5());
        STEPS.put("模板步骤6", new Step6());
        STEPS.put("图像灰度化", new GrayStep());
        STEPS.put("图像二值化", new BinaryStep());
        STEPS.put("维度转换", new TranDimStep());
        STEPS.put("一维数组定位", new PositioningStep());
        STEPS.put("图片降噪", new DeNoiseStep());
        STEPS.put("去除边框", new RemoveFrameStep());
        STEPS.put("图像裁剪", new SubImageStep());
        STEPS.put("图像压缩", new CompressStep());
        STEPS.put("图像识别", new DiscernStep());
        STEPS.put("QRCode识别", new DiscernQRStep());
        STEPS.put("正常二值化", new BinaryFinalStep());
        STEPS.put("高斯模糊", new GaussianStep());
        STEPS.put("还原图像", new ReductionStep());

    }


    public String flows;

    Flow(String flows) {
        this.flows = flows;
    }


    public static final Map<Flow, Map<Class<? extends IHandler>, StepNode>> FLOW_POOL = new HashMap<>();
}
