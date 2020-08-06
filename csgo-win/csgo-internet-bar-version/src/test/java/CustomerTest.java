import bean.RegisterBean;
import com.alibaba.fastjson.JSON;
import config.EnumExcel;
import config.EnumHttp;
import ddt.CustomerDDT;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import request.Customer;
import util.HttpUtils;
import util.VerifyCode;

public class CustomerTest {

    private String resp;
    private String token;

    @BeforeClass
    public void before(){
        try {
            token = JSON.parseObject(Customer.login(new RegisterBean(EnumHttp.DEVICE_ID.getVal()
                    ,EnumHttp.LOGIN_MOBILE.getVal(),null,null,EnumHttp.LOGIN_PWD.getVal())
                    ,null,"登录")).getJSONObject("result").getString("token");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(dataProviderClass = CustomerDDT.class,dataProvider = "register")
    public void register(RegisterBean reg, String expect, String testDesc){
        try {
            resp = Customer.register(reg,null,testDesc);
            HttpUtils.addAllureResp(resp);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Assert.assertEquals(resp,expect);
    }

    @Test(dataProviderClass = CustomerDDT.class,dataProvider = "login")
    public void login(RegisterBean reg, String expect, String testDesc){
        try {
            resp = Customer.login(reg,null,testDesc);
            HttpUtils.addAllureResp(resp);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (testDesc.contains("正常数据"))
        {
//            token = JSON.parseObject(resp).getJSONObject("result").getString("token");
            Assert.assertTrue(JSON.parseObject(resp).containsKey(expect));
        }else {
            Assert.assertEquals(resp,expect);
        }
    }

    @Test(dataProviderClass = CustomerDDT.class,dataProvider = "resetTradePwd")
    public void resetPassword(RegisterBean reg, String expect, String testDesc){
        try {
            if ("图形验证码错误".equals(expect)){
                reg.setGraphicCode(VerifyCode.getGraphicCode(reg.getDeviceId()));
            }
            if ("短信验证码错误".equals(expect)){
                reg.setMessageCode(VerifyCode.getMessageCode(reg.getDeviceId(),reg.getMobile()));
            }
            resp = Customer.resetPwd(reg,null,testDesc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(resp,expect);

    }

    @Test(dataProvider = "userInfo")
    public void getUserInfo(String token,String expect,String testDesc){
        try {
            resp = Customer.getUserInfo(token,testDesc);
            HttpUtils.addAllureResp(resp);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if ("通过token获取用户信息".equals(testDesc))
        {
            Assert.assertTrue(JSON.parseObject(resp).containsKey(expect));
        }else {
            Assert.assertEquals(resp, expect);
        }
    }

    @Test(dataProvider = "tradeUrl")
    public void setTradeUrl(String testCase, String expect){
        try {
            if (JSON.parseObject(testCase).getString("title").equals("token为空"))
            {
                resp = Customer.setTradeUrl(testCase, null);
            }else {
                resp = Customer.setTradeUrl(testCase, token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(resp,expect);
    }

    @DataProvider
    public Object[][] userInfo(){
        return new Object[][]{
                {token,"result","通过token获取用户信息"},
                {null,"{\"error\":{\"code\":106001,\"msg\":\"用户不存在\"}}","token为null"},
                {token + "errorToken","{\"error\":{\"code\":106001,\"msg\":\"用户不存在\"}}","token错误"},
        };
    }

    @DataProvider
    public Object[][] tradeUrl() throws Exception {
        return HttpUtils.dataProvider(EnumExcel.FILE_PATH.getVal(),0,"设置交易连接");
    }
}
