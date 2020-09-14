package com.zhihuishu;

import jodd.util.StringUtil;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * @author ：SunX
 * @date ：2020/9/10 13:53
 * @description：TODO
 */
public final class GetAgentStr extends UDF {
    public static String evaluate(String userAgentString) {
        if (StringUtil.isEmpty(userAgentString)) {
            return null;
        }
        return  userAgentString;

    }
}
