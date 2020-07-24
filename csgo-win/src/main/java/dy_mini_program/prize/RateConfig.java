package dy_mini_program.prize;

import config.EnumHttp;
import util.HttpUtils;

public class RateConfig {

    private static final String RATE_URL = EnumHttp.API_DOMAIN.getVal() + "prize/rate";

    public static String rateConfig(String array, String author) throws Exception {
        return HttpUtils.analysis(HttpUtils.doPut(RATE_URL,array,author));
    }
}
