package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import config.EnumHttp;

public class VerifyCode {

    private static final String DOMAIN = EnumHttp.API_DOMAIN.getVal();
    private static final String GRAPHIC_CODE_URL = DOMAIN + "/user/getGraphicCaptcha";
    private static final String MESSAGE_CODE_URL = DOMAIN + "/user/getMessageCaptcha";

    public static String getMessageCode(String deviceId,String mobile) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("deviceId",deviceId);
        obj.put("mobile",mobile);
        return JSON.parseObject(HttpUtils.analysis(HttpUtils.doPost(MESSAGE_CODE_URL,obj.toJSONString(),null,null)))
                .getJSONObject("result").getString("captcha");
    }

    public static String getGraphicCode(String deviceId) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("deviceId",deviceId);
        return JSON.parseObject(HttpUtils.analysis(HttpUtils.doPost(GRAPHIC_CODE_URL,obj.toJSONString(),null,null)))
                .getJSONObject("result").getString("captcha");
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getGraphicCode("123456789987654321123456"));
    }
}
