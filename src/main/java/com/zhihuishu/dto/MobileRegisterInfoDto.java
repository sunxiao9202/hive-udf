package com.zhihuishu.dto;


import java.io.Serializable;

public class MobileRegisterInfoDto implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 归属地省份
     */
    private String province;
    /**
     * 省份编码
     */
    private String provinceCode;
    /**
     * 归属地城市
     */
    private String city;
    /**
     * 市区编码
     */
    private String cityCode;
    /**
     * 省份编码
     */
    private String postCode;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
