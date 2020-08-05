package dy_mini_program_test;

import com.alibaba.fastjson.JSONObject;
import config.EnumHttp;
import dy_mini_program.about_custom.DyLogin;
import dy_mini_program.sys_setting.SysSettingFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.HttpUtils;

import java.io.File;
import java.io.IOException;

public class SysSettingTest {
    private final Logger log = LoggerFactory.getLogger(SysSettingTest.class);
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

    @Test
    public void uploadBanner(){
        try {
            JSONObject status = SysSettingFlow.uploadBanner(new File("D:\\img\\1.png"),token);
            HttpUtils.addAllureResp(status.toJSONString());
            Assert.assertEquals("done",status.getString("status"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dataProvider = "noticeOrRule")
    public void noticeConfig(String content){
        try {
            if ("".equals(content)){
                content = null;
            }
            JSONObject notice = SysSettingFlow.noticeConfig(content,token);
            HttpUtils.addAllureResp(notice.toJSONString());
            Assert.assertEquals(content,notice.getString("notice"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(dataProvider = "noticeOrRule")
    public void ruleConfig(String content){
        try {
            if ("".equals(content)){
                content = null;
            }
            JSONObject rule = SysSettingFlow.ruleConfig(content,token);
            HttpUtils.addAllureResp(rule.toJSONString());
            Assert.assertEquals(rule.getString("rule"),content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DataProvider(name = "noticeOrRule")
    public Object[][] noticeData(){
        return new Object[][]{
                {"1:!@#$%^&*()_+><?:123456789\n2:test test test test"},
                {"张之强、孔庆山、孔繁虎、李佳佳、石家庄坤伯瑞酒店管理有限公司、石家庄市新华区北二环西路89号星河御城15号住宅楼1-103（冀（2018）" +
                        "石家庄市不动产权第0029272号）不动产的租赁人、抵押权人及其他各利害关系人：关于申请执行人石家庄融乐典当行有限公司与被执行人张之强、" +
                        "孔庆山、孔繁虎、李佳佳、石家庄坤伯瑞酒店管理有限公司民间借贷纠纷一案，石家庄市长安区人民法院拟对被执行人张之强名下位于石家庄市新华区北二环西" +
                        "路89号星河御城15号住宅楼1-103（冀（2018）石家庄市不动产权第0029272号）不动产的价值进行评估，现向你们公告送达（2020）冀0102执1512号执行" +
                        "裁定书、（2020）冀0102执1512号执行通知书、报告财产令、财产申报表、纳入失信被执行人名单告知书、限制高消费令、执行裁定书【拍卖被执行人张之强名" +
                        "下位于石家庄市新华区北二环西路89号星河御城15号住宅楼1-103不动产（证号：冀（2018）石家庄市不动产权第0029272号）】、选择评估机构通知、勘验现场" +
                        "通知及领取评估报告的时间，自公告发出之日起经过60日即视为送达。自公告期满次日起第5日（遇法定假日顺延）上午10时到石家庄市长安区人民法院司法鉴定技" +
                        "术室（地址：石家庄市建华北大街138号百川大厦诉讼服务中心。法官王建勇，电话：0311-85051562）选取评估机构；于公告期满次日起第15日（遇法定假日顺延）" +
                        "下午15时到标的物所在地对拟评估标的物进行现场勘验；于公告期满次日起第30日（遇法定假日顺延）上午10时到本院1315室领取评估报告。评估报告异议期为自收到" +
                        "评估报告之日起10日内向本院提交。未到场及逾期未提异议，将被视为放弃相关权利"},
                {""}
        };
    }
}
