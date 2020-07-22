package dy_mini_program_test;

import bean.PrizeConfigBean;
import config.EnumHttp;
import dy_assert.AssertSql;
import dy_mini_program.about_custom.DyLogin;
import dy_mini_program.prize.PrizeConfigFlow;
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
            String assertText = PrizeConfigFlow.prizeConfigFlow(bean, token).getString("id");
            log.info("id >> {}", assertText);
            Assert.assertEquals(expect,SqlUtils.select(AssertSql.prizeAssert(assertText)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(description = "奖品配置列表")
    public void prizeConfigList(){
        try {
            int size = PrizeConfigFlow.getPrizeConfigList(token).size();
            Assert.assertEquals(8,size);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void uploadImg(){
        try {
            String status = PrizeConfigFlow.uploadPrizeImg(new File(EnumHttp.IMG_PATH.getVal()),token).getString("status");
            Assert.assertEquals("done",status);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
