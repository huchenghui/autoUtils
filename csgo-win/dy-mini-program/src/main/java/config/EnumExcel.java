package config;

public enum EnumExcel {

    FAILED("失败","RED"),
    SUCCESS("成功","GREEN"),
    SKIP("跳过","GREY"),
    FILE_PATH("文件路径","D:\\ddt\\dy_mini_program.xlsx")
    ;

    private String desc;
    private String val;

    EnumExcel(String desc, String val) {
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
