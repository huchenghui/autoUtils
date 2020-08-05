package dy_mini_program.about_custom;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import config.EnumHttp;
import util.Encrypt;
import util.HttpUtils;

public class DyLogin {
    private static final String LOGIN_URL = EnumHttp.API_DOMAIN.getVal() + "auth/admin";

    //管理后台登录
    public static JSONObject doLogin(String userName,String password) throws Exception {
        String str = "{ \"data\": \"{\\\"username\\\":\\\""+userName+"\\\", \\\"password\\\":\\\""+password+"\\\"}\"}";
        return JSON.parseObject(HttpUtils.analysis(HttpUtils.doPost(LOGIN_URL, Encrypt.doEncrypt(str),null)));
    }

}
