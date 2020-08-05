package dy_mini_program_test;

import bean.PrizeConfigBean;
import bean.RateConfigBean;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import config.EnumHttp;
import dy_assert.AssertSql;
import dy_mini_program.about_custom.DyLogin;
import dy_mini_program.prize.PrizeConfigFlow;
import dy_mini_program.prize.RateConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import util.HttpUtils;
import util.SqlUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class PrizeConfigTest {

    private final Logger log = LoggerFactory.getLogger(PrizeConfigTest.class);
    private String token;


    @BeforeClass
    public void beforeClass(){
        try {
            token = DyLogin.doLogin(EnumHttp.USERNAME.getVal(),EnumHttp.PASSWORD.getVal()).getString("access_token");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void afterClass(){
        try {
            HttpUtils.consumerResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dataProviderClass = PrizeConfigBean.class,dataProvider = "prizeBean")
    public void prizeConfig(PrizeConfigBean bean,String expect){
        try {
            JSONObject assertText = PrizeConfigFlow.prizeConfigFlow(bean, token);
            log.info("id >> {}", assertText);
            HttpUtils.addAllureResp(assertText.toJSONString());
            Assert.assertEquals(expect,SqlUtils.select(AssertSql.prizeAssert(assertText.getString("id"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(description = "prize config list")
    public void prizeConfigList(){
        try {
            JSONArray arr = PrizeConfigFlow.getPrizeConfigList(token);
            HttpUtils.addAllureResp(arr.toJSONString());
            Assert.assertEquals(8,arr.size());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test(description = "prize picture upload")
    public void uploadImg(){
        try {
            JSONObject status = PrizeConfigFlow.uploadPrizeImg(new File(EnumHttp.IMG_PATH.getVal()),token);
            HttpUtils.addAllureResp(status.toJSONString());
            Assert.assertEquals("done",status.getString("status"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dataProviderClass = RateConfigBean.class,dataProvider = "rate")
    public void rateConfig(String rate,String expect){
        try {
            String res = RateConfig.rateConfig(rate,token);
            HttpUtils.addAllureResp(res);
            if (res.contains("statusCode")){
                Assert.assertEquals(expect,JSON.parseObject(res).getString("statusCode"));
            }else {
                Assert.assertEquals(Integer.parseInt(expect),JSON.parseArray(res).size());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
