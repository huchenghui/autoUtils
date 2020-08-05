package dy_mini_program.sys_setting;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import config.EnumHttp;
import util.HttpUtils;

import java.io.File;
import java.io.IOException;

public class SysSettingFlow {
    private static final String DOMAIN = EnumHttp.API_DOMAIN.getVal();
    private static final String BANNER_URL = DOMAIN + "system-setting/banner";
    private static final String NOTICE_URL = DOMAIN + "system-setting/notice";
    private static final String RULE_URL = DOMAIN + "system-setting/rule";

    //上传banner
    public static JSONObject uploadBanner(File filePath,String token) throws IOException {
        return JSON.parseObject(HttpUtils.analysis(HttpUtils.doUpload(BANNER_URL,null,"file",filePath,token)));
    }

    //公告
    public static JSONObject noticeConfig(String content,String token) throws Exception {
        JSONObject json = new JSONObject();
        json.put("notice",content);
        return JSON.parseObject(HttpUtils.analysis(HttpUtils.doPost(NOTICE_URL,json.toJSONString(),token)));
    }

    //规则
    public static JSONObject ruleConfig(String content,String token) throws Exception {
        JSONObject json = new JSONObject();
        json.put("rule",content);
        return JSON.parseObject(HttpUtils.analysis(HttpUtils.doPost(RULE_URL,json.toJSONString(),token)));
    }
}
