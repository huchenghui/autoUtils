package dy_mini_program.about_custom;

import config.EnumHttp;
import util.Encrypt;
import util.HttpUtils;

public class UpdatePwd {

    private static final String UPDATE_PWD_URL = EnumHttp.API_DOMAIN.getVal() + "auth/pwd";

    public static String updatePwd(String oldPwd,String newPwd,String token) throws Exception {
        String str = "{ \"data\": \"{\\\"oldPwd\\\":\\\""+oldPwd+"\\\", \\\"newPwd\\\":\\\""+newPwd+"\\\"}\"}";
        return HttpUtils.analysis(HttpUtils.doPut(UPDATE_PWD_URL, Encrypt.doEncrypt(str),token));
    }
}
