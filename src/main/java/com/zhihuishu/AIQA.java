package com.zhihuishu;

import org.apache.hadoop.hive.ql.exec.UDF;

import com.zhihuishu.util.AI_QA_UTIL;

/**
 * @author ：SunX
 * @date ：2020/9/10 13:53
 * @description：TODO
 */
public final class AIQA extends UDF {
    public static float evaluate(String str,String target) {
        return AI_QA_UTIL.similarity(str, target);
    }
    
    public static float evaluate(String str,String target,float bfb) {
        return AI_QA_UTIL.similarity(str, target,bfb);
    }
}
