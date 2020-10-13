package com.zhihuishu;

import com.alibaba.fastjson.JSONObject;
import com.zhihuishu.dto.MobileRegisterInfoDto;
import com.zhihuishu.util.OkHttpClientFactory;
import com.zhihuishu.util.StringHelper;
import jodd.util.StringUtil;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.io.IOException;

/**
 * @author ：SunX
 * @date ：2020/9/10 13:53
 * @description：获取手机号码归属地
 */
public final class GetMobileCity extends UDF {
    private static final String APP_ID = "";
    private static final String APP_KEY = "";

    public static String evaluate(String mobile) {
        if (StringUtil.isEmpty(mobile)) {
            return null;
        }
        // 构建请求
        Request request = new Request.Builder()
                .url("https://api.253.com/open/unn/teladress")
                .post(new FormBody.Builder()
                        .add("appId", APP_ID)
                        .add("appKey", APP_KEY)
                        .add("mobile", mobile)
                        .add("orderNo", StringHelper.uuid())
                        .build())
                .build();

        // 发送请求
        Response response = null;
        try {
            response = OkHttpClientFactory.getInstance().newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 获取手机号归属地
        try {
            final String rs = response.body().string();
            if (!StringUtils.isEmpty(rs)) {
                final JSONObject jsonObject = JSONObject.parseObject(rs);
                if (jsonObject != null && !StringUtils.isEmpty(jsonObject.getString("data"))) {
                    MobileRegisterInfoDto dto = JSONObject.parseObject(jsonObject.getString("data"), MobileRegisterInfoDto.class);
                    if (dto != null && StringUtils.isNotBlank(dto.getMobile())) {
                        return dto.getProvince() + "|" + dto.getCity();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
