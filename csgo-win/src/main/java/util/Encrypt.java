package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import config.EnumHttp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Encrypt {
    private static final Logger log = LoggerFactory.getLogger(Encrypt.class);
    private static final String ENCRYPT_URL = EnumHttp.API_DOMAIN.getVal() + "crypto/sm2encryt";

    public static JSONObject doEncrypt(String str) throws Exception {
        log.info("加密数据 >> {}",str);
        return JSON.parseObject(HttpUtils.analysis(HttpUtils.doPost(ENCRYPT_URL,JSON.parseObject(str),null)));
    }


}
