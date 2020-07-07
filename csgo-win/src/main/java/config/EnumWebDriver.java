package config;

public enum EnumWebDriver {
    WEBDRIVER_PATH("驱动地址","D:\\testng\\chromedriver.exe");

    private String desc;
    private String val;

    EnumWebDriver(String desc, String val) {
        this.desc = desc;
        this.val = val;
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
