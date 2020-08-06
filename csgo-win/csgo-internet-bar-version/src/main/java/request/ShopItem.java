package request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import config.EnumHttp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileUtils;
import util.HttpUtils;
import util.SqlUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ShopItem {

    private static final Logger log = LoggerFactory.getLogger(ShopItem.class);
    private static final String DOMAIN = EnumHttp.API_DOMAIN.getVal();
    private static final String CONDITIONS_URL = DOMAIN + "/itemEnum/getFilters";


    public static JSONObject searchConditions() throws Exception {
        return JSON.parseObject(HttpUtils.analysis(HttpUtils.doPost(CONDITIONS_URL,null,null,null)));
    }

    public static JSONObject searchShopItem(String testCase) throws Exception {
        return JSON.parseObject(HttpUtils.runCaseByExcel(testCase,null));
    }

    public static void main(String[] args) throws Exception {
        FileUtils f = new FileUtils("d:\\ddt\\csgo_internet_bar_version.xlsx", 0);
        JSONObject params = JSON.parseObject(JSON.toJSONString(f.readLine(12))).getJSONObject("parameter_data");
        String id;
        String comPart = "select count(0) from shopItem s,item i where s.itemId = i.id and s.enable = 1 and s.shopPrice > 0 and ";
        JSONObject objs = searchConditions().getJSONObject("result");
        String key;
        StringBuilder sql;
        StringBuilder childSql;
        List<String> list = new LinkedList<>();
        JSONArray arr;
        for (Map.Entry<String,Object> m : objs.entrySet())
        {
            arr = objs.getJSONArray(m.getKey());
            key = m.getKey();
            if (key.equals("exterior"))
            {
                for (Object s : arr)
                {
                    id = JSON.parseObject(s.toString()).getString("id");
                    sql = new StringBuilder(comPart);
                    sql.append("i.exterior = ");
                    sql.append("\"").append(id).append("\"");
                    log.info("search Exterior sql is >> {}",sql.toString());
                    params.put("exterior",id);
                    log.info("request param is >> {}",params);
                    SqlUtils.select(sql.toString());
                }
            }
            else if ("quality".equals(key))
            {
                for (Object s : arr)
                {
                    id = JSON.parseObject(s.toString()).getString("id");
                    sql = new StringBuilder(comPart);
                    sql.append("i.quality = ");
                    sql.append("\"").append(id).append("\"");
                    log.info("search Quality sql is >> {}",sql.toString());
                    params.put("quality",id);
                    log.info("request param is >> {}",params);
                    SqlUtils.select(sql.toString());
                }
            }
            else if ("rarity".equals(key))
            {
                for (Object s : arr)
                {
                    id = JSON.parseObject(s.toString()).getString("id");
                    sql = new StringBuilder(comPart);
                    sql.append("i.rarity = ");
                    sql.append("\"").append(id).append("\"");
                    log.info("search Rarity sql is >> {}",sql.toString());
                    params.put("rarity",id);
                    log.info("request param is >> {}",params);
                    SqlUtils.select(sql.toString());
                }
            }
            else if ("weapon".equals(key))
            {
                for (Object s : arr)
                {
                    id = JSON.parseObject(s.toString()).getString("id");
                    sql = new StringBuilder(comPart);
                    sql.append("i.weapon = ");
                    sql.append("\"").append(id).append("\"");
                    log.info("search Weapon sql is >> {}",sql.toString());
                    params.put("weapon",id);
                    log.info("request param is >> {}",params);
                    SqlUtils.select(sql.toString());
                }
            }
            else if ("type".equals(key))
            {
                for (Object o : arr){
                    id = JSON.parseObject(o.toString()).getString("id");
                    sql = new StringBuilder(comPart);
                    sql.append("i.type = ");
                    sql.append("\"").append(id).append("\"");
                    log.info("search Type sql is >> {}",sql.toString());
                    params.put("type",id);
                    log.info("request param is >> {}",params);
                    SqlUtils.select(sql.toString());
                    if (JSON.parseObject(o.toString()).containsKey("childList"))
                    {
                        for (Object child : JSON.parseObject(o.toString()).getJSONArray("childList")){
                            id = JSON.parseObject(child.toString()).getString("id");
                            childSql = new StringBuilder(sql.toString());
                            childSql.append(" and i.weapon = ");
                            childSql.append("\"").append(id).append("\"");
                            log.info("search childList sql is >> {}",sql.toString());
                            params.put("weapon",id);
                            log.info("request param is >> {}",params);
                            SqlUtils.select(childSql.toString());
                        }
                    }
                }
            }
        }

//        FileUtils f = new FileUtils("d:\\ddt\\csgo_internet_bar_version.xlsx", 0);
//        for (Map<String,Object> m : f.read("商城饰品查询")){
//            params = JSON.parseObject(m.get("parameter_data").toString());
//            sql = JSON.parseObject(m.get("sql").toString());
//        }
    }
}
