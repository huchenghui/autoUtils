package config;

public enum EnumMysql {
    USER_NAME("用户名","root"),
    PASS_WORD("密码","cdtech-remote@11skins.com"),
    DRIVER_PATH("驱动地址","com.mysql.cj.jdbc.Driver"),
    DOMAIN("连接地址","admin.looveh.xyz"),
    DATABASE("数据库名","/csgo-op");

    private String desc;
    private String val;

    EnumMysql(String description,String value){
        this.desc = description;
        this.val = value;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
