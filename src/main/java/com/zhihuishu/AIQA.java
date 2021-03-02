package com.zhihuishu;

import org.apache.hadoop.hive.ql.exec.UDF;

import com.zhihuishu.util.AI_QA_UTIL;

/**
 * create function udf_aiqa AS 'com.zhihuishu.AIQA';
 */
public final class AIQA extends UDF {
    public static float evaluate(String str,String target) {
    	try {
    		return AI_QA_UTIL.similarity(str, target);
		} catch (Exception e) {
			return 0;
		}
        
    }
    
    public static float evaluate(String str,String target,float bfb) {
    	try {
    		return AI_QA_UTIL.similarity(str, target,bfb);
		} catch (Exception e) {
			return 0;
		}
        
    }
}
