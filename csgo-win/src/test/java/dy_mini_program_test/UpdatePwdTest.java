package dy_mini_program_test;

import com.alibaba.fastjson.JSON;
import dy_mini_program.about_custom.DyLogin;
import dy_mini_program.about_custom.Register;
import dy_mini_program.about_custom.UpdatePwd;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.HttpUtils;

import java.io.IOException;
import java.util.Random;

public class UpdatePwdTest {
    private String userName;
    private String pwd;
    private String token;
    private String newPwd = "123456  !--!@#$%^&*  (<>?--!!";

    @BeforeClass
    public void before(){
        Random r = new Random();
        userName = "hch" + r.nextInt(10000);
        pwd = "test123456";
        try {
            Register.register(userName,pwd);
            token = DyLogin.doLogin(userName,pwd).getString("access_token");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void after(){
        try {
            HttpUtils.consumerResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dataProvider = "updatePwd")
    public void updatePwd(String oldPwd,String newPwd,String expect){
        try {
            String str = UpdatePwd.updatePwd(oldPwd,newPwd,token);
            if (str.length() > 0){
                Assert.assertEquals(JSON.parseObject(str).getString("statusCode"),expect);
            }else {
                Assert.assertEquals(str,expect);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DataProvider(name = "updatePwd")
    public Object[][] updateData(){
        return new  Object[][]{
                {pwd,pwd,""},
                {pwd,newPwd,""},
                {"errorPwd","123456","400"},
                {"  ","123456","400"},
                {"  ","  ","400"},
                {"","","400"},
                {"","123456","400"},
                {newPwd,"","400"},
                {newPwd,"  ","400"}
        };
    }
}
