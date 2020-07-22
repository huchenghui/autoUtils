package dy_mini_program_test;

import com.alibaba.fastjson.JSON;
import dy_mini_program.about_custom.Register;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.HttpUtils;

import java.io.IOException;
import java.util.Random;

public class RegisterTest {
    private Random r;

    @BeforeClass
    public void before(){
        r = new Random();
    }

    @AfterClass
    public void after(){
        try {
            HttpUtils.consumerResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dataProvider = "register")
    public void register(String userName,String password,String expect){
        try {

            String statusCode = null;
            String resp = Register.register(userName, password);
            if ("".equals(userName) || userName.trim().equals("") || "".equals(password) || password.trim().equals("")){
                try {
                    statusCode = JSON.parseObject(resp).getString("statusCode");
                }catch (NullPointerException e){
                    Assert.assertEquals(expect,statusCode);
                }
                if (statusCode.equals("409")){
                    Assert.fail(resp);
                }
                Assert.assertEquals(expect,statusCode);
            }else {
                Assert.assertEquals(expect,resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DataProvider(name = "register")
    public Object[][] regData(){

        return new Object[][]{
                {"hch"+r.nextInt(10000),"123456",""},
                {"qazwsxedcrfvtgbyhnujmik,ol.p;/[']!@#$%^&*()"+r.nextInt(10000),"123456",""},
                {"自动化测试"+r.nextInt(10000),"qazwsxedcrfvtgbyhnujmik,ol.p;/[']!@#$%^&*()",""},
                {"自动化测试"+r.nextInt(10000),"自动化测试自动化测试自动化测试自动化测试自动化测试自动化测试自动化测试自动化测试自动化测试",""},
                {"","","400"},
                {"  ","  ","400"},
                {"  ","用户名为两个空格123456哈撒给!@#$%^","400"},
                {"密码为两个空格"+r.nextInt(10000),"  ","400"}
        };
    }
}
