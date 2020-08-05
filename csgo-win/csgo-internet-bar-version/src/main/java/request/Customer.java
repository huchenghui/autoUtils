package request;

import bean.RegisterBean;
import com.alibaba.fastjson.JSON;
import config.EnumHttp;
import util.HttpUtils;

public class Customer {

    private static final String DOMAIN = EnumHttp.API_DOMAIN.getVal();
    private static final String REG_URL = DOMAIN + "/user/register";
    private static final String LOGIN_URL = DOMAIN + "/user/login";
    private static final String GET_USER_INFO_URL = DOMAIN + "/user/getUserInfo";
    private static final String SET_TRADE_URL = DOMAIN + "/user/setSteamTradeUrl";

    public static String register(RegisterBean reg,String author,String desc) throws Exception {
        return HttpUtils.analysis(HttpUtils.doPost(REG_URL,JSON.toJSONString(reg),author,desc));
    }

    public static String login(RegisterBean reg,String author,String desc) throws Exception {
        return HttpUtils.analysis(HttpUtils.doPost(LOGIN_URL,JSON.toJSONString(reg),author,desc));
    }

    public static String getUserInfo(String author,String desc) throws Exception {
        return HttpUtils.analysis(HttpUtils.doPost(GET_USER_INFO_URL,null,author,desc));
    }

    public static String setTradeUrl(String testCase,String author) throws Exception {
        return HttpUtils.runCaseByExcel(testCase,author);
    }

}
