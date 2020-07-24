package config;

public enum EnumMysql {
    
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
