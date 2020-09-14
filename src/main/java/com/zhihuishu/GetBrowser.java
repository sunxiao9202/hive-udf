package com.zhihuishu;

import eu.bitwalker.useragentutils.UserAgent;
import jodd.util.StringUtil;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * @author ：SunX
 * @date ：2020/9/10 13:53
 * @description：TODO
 */
public final class GetBrowser extends UDF {
    public static String evaluate(String userAgentString) {
        if (StringUtil.isEmpty(userAgentString)) {
            return null;
        }
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);
        //操作系统|浏览器
        return userAgent.getOperatingSystem().toString()+"|"+userAgent.getBrowser().toString();
    }
}
