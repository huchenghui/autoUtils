package dy_mini_program.about_custom;

import config.EnumHttp;
import util.HttpUtils;

public class Logout {

    private static final String LOGOUT_URL = EnumHttp.API_DOMAIN.getVal() + "auth";

    public static String logout(String token) throws Exception {
        return HttpUtils.analysis(HttpUtils.doDelete(LOGOUT_URL,null,token));
    }

}
