package imp;

import mapper.IElements;

public enum EnumLogin implements IElements{
    USER_NAME("用户名","input[aria-label=\"登录账号\"]"),
    PASSWORD("密码","input[aria-label=\"登录密码\"]"),
    LOGIN_BTN("登录按钮","div.q-btn__content");

    private String desc;
    private String elPath;

    EnumLogin(String desc, String elPath) {
        this.desc = desc;
        this.elPath = elPath;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getElPath() {
        return elPath;
    }

    public void setElPath(String elPath) {
        this.elPath = elPath;
    }

    @Override
    public String getElementPath() {
        return this.getElPath();
    }
}
