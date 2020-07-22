package dy_mini_program_test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import dy_mini_program.about_custom.DyLogin;
import dy_mini_program.about_custom.Logout;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginAndLogoutTest {

    private String token;

    @Test(dataProvider = "login")
    public void login(String userName,String password,String expect){
        try {
            JSONObject jsonObject = DyLogin.doLogin(userName, password);
            if (jsonObject != null && jsonObject.containsKey("access_token")){
                    token = jsonObject.getString(expect);
                Assert.assertTrue(jsonObject.containsKey(expect));
            }
            if (jsonObject != null && jsonObject.containsKey("statusCode")) {
                Assert.assertEquals(jsonObject.getString("statusCode"),expect);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(dataProvider = "logout")
    public void logout(String access_token,String expect){
        try {
            String str = Logout.logout(access_token);
            if (!"".equals(str)){
                Assert.assertEquals(expect, JSON.parseObject(str).getString("statusCode"));
            }
            Assert.assertEquals(expect,str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DataProvider(name = "login")
    public Object[][] loginData(){
        return new Object[][]{
                {"","","400"},
                {"  ","  ","400"},
                {"errorUsername","errorPassword","400"},
                {"admin","errorPassword","400"},
                {"errorUsername","123456","400"},
                {"admin","123456","access_token"}
        };
    }

    @DataProvider(name = "logout")
    public Object[][] logoutData(){
        return new Object[][]{
                {token,""},
                {"qwert123asdf456zxcv789yuo14mjfk465gk32f7g6k","401"}
        };
    }
}
