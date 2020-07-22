package dy_mini_program.prize;

import bean.PrizeConfigBean;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import config.EnumHttp;
import data.dy_mini_program_data.prize_data.PrizeConfigData;
import util.HttpUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class PrizeConfigFlow {

    private static final String DOMAIN = EnumHttp.API_DOMAIN.getVal();
    private static final String PRIZE_URL = DOMAIN + "prize";
    private static final String UPLOAD_URL = DOMAIN + "prize/img";

    //奖品配置
    public static JSONObject prizeConfigFlow(PrizeConfigBean bean, String author) throws Exception {
        return JSON.parseObject(HttpUtils.analysis(HttpUtils.doPost(PRIZE_URL, PrizeConfigData.prizeConfig(bean),author)));
    }

    //奖品配置列表
    public static JSONArray getPrizeConfigList(String token) throws IOException, URISyntaxException {
        return JSON.parseArray(HttpUtils.analysis(HttpUtils.doGet(PRIZE_URL,null,token)));
    }

    public static JSONObject uploadPrizeImg(File file,String token) throws IOException {
        return JSON.parseObject(HttpUtils.analysis(HttpUtils.doUpload(UPLOAD_URL,null,"prizeImg",file,token)));
    }
}
