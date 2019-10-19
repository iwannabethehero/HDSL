package com.hanlzz.findqr.step;


import com.hanlzz.findqr.common.IStep;
import com.hanlzz.findqr.common.StepResult;

import java.util.ArrayList;
import java.util.Map;

import static com.hanlzz.findqr.Main.LENGTH;

/**
 * 一维数组定位
 *
 * @author liets
 */
public class PositioningStep implements IStep {
    @Override
    public StepResult run(Map<String, Object> context) {
        int[] wids = (int[]) context.get("wids");
        int[] heis = (int[]) context.get("heis");

        ArrayList<Integer[]> boundWid = new ArrayList<>();
        ArrayList<Integer[]> boundHei = new ArrayList<>();
        int st = 0;
        while (st!=wids.length-1){
            Integer[] bound = getBound(wids, st);
            st = bound[1];
            if (bound[1] - bound[0] < 100){
                continue;
            }
            boundWid.add(bound);
        }

        st=0;
        while (st!=heis.length-1){
            Integer[] bound = getBound(heis, st);
            st = bound[1];
            if (bound[1] - bound[0] < 100){
                continue;
            }
            boundHei.add(bound);
        }


        context.put("boundWid", boundWid);
        context.put("boundHei", boundHei);

        return StepResult.continueFlow();
    }

    private Integer[] getBound(int[] arr,int st) {
        final int len = arr.length;
        int sum = 0, s = st, e = len - 1, max = 0, p = st;
        boolean flag = false;
        for (int i = st; i < len; i++) {
            sum += arr[i] == 0 ? -1 : 1;
            sum = Math.max(0, sum);
            if (sum > max) {
                max = sum;
                p = i;
            }
            if (sum == 0) {
                s = i;
            }
            if (sum > LENGTH && !flag) {
                flag = true;
            }

            if (flag && sum == max-30) {

                return new Integer[]{s, p};
            }

        }
        return new Integer[]{s, e};
    }

    @Override
    public boolean ignoreProxy() {
        return true;
    }
}
