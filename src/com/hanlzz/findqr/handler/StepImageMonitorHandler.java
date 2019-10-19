package com.hanlzz.findqr.handler;

import com.hanlzz.findqr.common.IStep;
import com.hanlzz.findqr.proxy.IHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author hlz
 */
public class StepImageMonitorHandler implements IHandler {

    private IStep step;

    private static final String PATH = "C:\\Users\\liets\\Desktop\\test\\output\\";

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object ans = null;
        if ("run".equals(method.getName())) {
//            System.out.println(" [" + step.getClass().getSimpleName() + " : " + step.getClass().getSimpleName() + "] : 开始执行 ...");
            long st = System.currentTimeMillis();
            ans = method.invoke(step, args);
            long end = System.currentTimeMillis();
//            System.out.println(" [" + step.getClass().getSimpleName() + " : " + step.getClass().getSimpleName() + "] : 执行完成,耗时 : " + (end - st));
            if (args != null && args.length > 0) {
                String sortedName = end + step.getClass().getSimpleName() + ".jpg";
                ImageIO.write((BufferedImage) ((Map) args[0]).get("image"), "jpg", new File(PATH + sortedName));
//                System.out.println("输出文件完成,文件名为 : " + sortedName);
            }
        }
        return ans;
    }

    StepImageMonitorHandler(IStep step){
        this.step = step;
    }

}
