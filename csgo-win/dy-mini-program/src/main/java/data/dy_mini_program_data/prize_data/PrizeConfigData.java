package data.dy_mini_program_data.prize_data;

import bean.PrizeConfigBean;
import com.alibaba.fastjson.JSONObject;

public class PrizeConfigData {

    public static String  prizeConfig(PrizeConfigBean bean){
        JSONObject json = new JSONObject();
        json.put("command",bean.getCommand());
        json.put("img",bean.getImg());
        json.put("location",bean.getLocation());
        json.put("name",bean.getName());
        json.put("rate",bean.getRate());
        json.put("type",bean.getType());
        json.put("wechat",bean.getWechat());
        return json.toJSONString();
    }


}
