package util;

import com.alibaba.fastjson.JSON;

import java.util.Map;

public class AllureTest {

    public static void main(String[] args) throws Exception {
        FileUtils f = new FileUtils("d:\\ddt\\csgo_internet_bar_version.xlsx", 0);
        for (Map<String,Object> m : f.read("商城饰品查询")){
            System.out.println(JSON.toJSONString(m));
        }
    }


}
