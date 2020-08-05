package dy_mini_program.about_custom;

import config.EnumHttp;
import util.Encrypt;
import util.HttpUtils;

public class Register {
    private static final String REGISTER_URL = EnumHttp.API_DOMAIN.getVal() + "auth/register";

    public static String register(String userName,String password) throws Exception {
        String str = "{ \"data\": \"{\\\"username\\\":\\\""+userName+"\\\", \\\"password\\\":\\\""+password+"\\\"}\"}";
        return HttpUtils.analysis(HttpUtils.doPost(REGISTER_URL, Encrypt.doEncrypt(str),null));
    }

}
