package util;

import config.EnumHttp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Encrypt {
    private static final Logger log = LoggerFactory.getLogger(Encrypt.class);
    private static final String ENCRYPT_URL = EnumHttp.API_DOMAIN.getVal() + "crypto/sm2encryt";

    public static String doEncrypt(String str) throws Exception {
        return HttpUtils.analysis(HttpUtils.doPost(ENCRYPT_URL,str,null));
    }


}
