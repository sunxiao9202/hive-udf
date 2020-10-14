package com.zhihuishu;

import com.alibaba.fastjson.JSONObject;
import com.zhihuishu.dto.MobileRegisterInfoDto;
import com.zhihuishu.util.HttpClientUtil;
import com.zhihuishu.util.StringHelper;
import jodd.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：SunX
 * @date ：2020/9/10 13:53
 * @description：获取手机号码归属地httpClient方式调用
 */
public final class GetMobileCityHttp extends UDF {
    private static final String APP_ID = "";
    private static final String APP_KEY = "";

    public static String evaluate(String mobile) {
        if (StringUtil.isEmpty(mobile)) {
            return "";
        }
        String url = "https://api.253.com/open/unn/teladress";
        Map<String, String> param = new HashMap<>();
        param.put("appId", APP_ID);
        param.put("appKey", APP_KEY);
        param.put("mobile", mobile);
        param.put("orderNo", StringHelper.uuid());
        // 获取手机号归属地
        try {
            final String rs = HttpClientUtil.post(url, param);
            if (!StringUtils.isEmpty(rs)) {
                final JSONObject jsonObject = JSONObject.parseObject(rs);
                if (jsonObject != null && !StringUtils.isEmpty(jsonObject.getString("data"))) {
                    MobileRegisterInfoDto dto = JSONObject.parseObject(jsonObject.getString("data"), MobileRegisterInfoDto.class);
                    if (dto != null && StringUtils.isNotBlank(dto.getMobile())) {
                        return dto.getProvince() + "|" + dto.getCity();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

}
