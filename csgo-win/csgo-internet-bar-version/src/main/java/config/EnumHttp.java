package config;

import util.HttpUtils;

public enum EnumHttp {

    MAX_TOTAL("最大连接数","40"),
    MAX_PRE_ROUTER("路由默认最大连接数","20"),
    TIME_OUT("超时时间","30000"),
    API_DOMAIN("域名","http://shop.storegames.net/api"),
    MOBILE("手机号","15700000000"),
    DEVICE_ID_23("23位设备号", HttpUtils.few(23)),
    DEVICE_ID_35("35位设备号", HttpUtils.few(35)),
    DEVICE_ID("有效位设备号", HttpUtils.few(26)),
    LOGIN_MOBILE("登录手机号","15756240257"),
    LOGIN_DEVICE_ID("登录手机号设备ID","123456789987654321123456"),
    LOGIN_PWD("登录密码","1qaz2wsx"),
    USERNAME("用户名","admin"),
    PASSWORD("密码","abc123"),
    IMG_PATH("图片路径","D:\\img\\1.png");
    ;


    private String desc;
    private String val;

    EnumHttp(String s, String s1) {
        this.desc = s;
        this.val = s1;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
