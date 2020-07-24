package util;

import com.alibaba.fastjson.JSONObject;
import dy_mini_program.about_custom.DyLogin;
import io.qameta.allure.Allure;
import listeners.Listener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({Listener.class})
public class AllureTest {

    @Test
    public void t(){
        try {
            JSONObject str = DyLogin.doLogin("admin", "admin");
            Allure.description("{{响应数据}}\n\n" + str.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
