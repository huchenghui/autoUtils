package config;

public enum EnumHttp {

    MAX_TOTAL("最大连接数","40"),
    MAX_PRE_ROUTER("路由默认最大连接数","20"),
    TIME_OUT("超时时间","30000")
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
