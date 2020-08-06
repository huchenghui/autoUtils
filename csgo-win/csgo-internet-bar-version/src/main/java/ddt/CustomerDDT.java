package ddt;

import bean.RegisterBean;
import config.EnumHttp;
import org.testng.annotations.DataProvider;
import util.HttpUtils;
import util.VerifyCode;

public class CustomerDDT {

    private final String MOBILE = EnumHttp.MOBILE.getVal();
    private final String LOGIN_MOBILE = EnumHttp.LOGIN_MOBILE.getVal();
    private final String LOGIN_DEVICE_ID = EnumHttp.LOGIN_DEVICE_ID.getVal();
    private final String LOGIN_PWD = EnumHttp.LOGIN_PWD.getVal();
    private final String DEVICE_ID_23 = EnumHttp.DEVICE_ID_23.getVal();
    private final String DEVICE_ID_35 = EnumHttp.DEVICE_ID_35.getVal();
    private final String DEVICE_ID = EnumHttp.DEVICE_ID.getVal();
    private final String PWD = EnumHttp.PASSWORD.getVal();

    @DataProvider
    public Object[][] register() throws Exception {
        return new Object[][]{
                {new RegisterBean(DEVICE_ID,MOBILE,VerifyCode.getGraphicCode(HttpUtils.few(26)), VerifyCode.getMessageCode(DEVICE_ID,MOBILE),PWD),"{\"status\":500,\"error\":{\"code\":107001,\"msg\":\"图形验证码已过期\"}}","注册设备ID和图形码设备ID不一致"},
                {new RegisterBean(DEVICE_ID,MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(DEVICE_ID,"15756240257"),PWD),"{\"status\":500,\"error\":{\"code\":107001,\"msg\":\"短信验证码已过期\"}}","注册手机号和发送短信验证码手机号不一致"},
                {new RegisterBean(DEVICE_ID,MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(HttpUtils.few(26),MOBILE),PWD),"{\"status\":500,\"error\":{\"code\":107001,\"msg\":\"短信验证码已过期\"}}","注册设备ID和发送短信验证码设备ID不一致"},
                {new RegisterBean(DEVICE_ID,MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), null,PWD),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"缺少messageCode\"}}","短信验证码为NULL"},
                {new RegisterBean(DEVICE_ID,MOBILE,null, VerifyCode.getMessageCode(DEVICE_ID,MOBILE),PWD),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"缺少graphicCode\"}}","图形验证码为NULL"},
                {new RegisterBean(DEVICE_ID,MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(DEVICE_ID,MOBILE),"111111"),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"密码错误(至少包含数字和字母,长度6~20位)\"}}","纯数字密码长度小于6位"},
                {new RegisterBean(DEVICE_ID,MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(DEVICE_ID,MOBILE),"1111111"),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"密码错误(至少包含数字和字母,长度6~20位)\"}}","纯数字密码长度等于6位"},
                {new RegisterBean(DEVICE_ID,MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(DEVICE_ID,MOBILE),"111111111111111111111111"),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"密码错误(至少包含数字和字母,长度6~20位)\"}}","纯数字密码长度大于20位"},
                {new RegisterBean(DEVICE_ID,MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(DEVICE_ID,MOBILE),"abcabc"),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"密码错误(至少包含数字和字母,长度6~20位)\"}}","纯字母密码长度小于6位"},
                {new RegisterBean(DEVICE_ID,MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(DEVICE_ID,MOBILE),"abcabcd"),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"密码错误(至少包含数字和字母,长度6~20位)\"}}","纯字母密码长度等于6位"},
                {new RegisterBean(DEVICE_ID,MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(DEVICE_ID,MOBILE),"abcabcdabcabcdabcabcdabc"),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"密码错误(至少包含数字和字母,长度6~20位)\"}}","纯字母密码长度大于20位"},
                {new RegisterBean(DEVICE_ID,MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(DEVICE_ID,MOBILE),"111ac"),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"密码错误(至少包含数字和字母,长度6~20位)\"}}","数字+字母组合密码长度小于6位"},
                {new RegisterBean(DEVICE_ID,MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(DEVICE_ID,MOBILE),"1#$ac"),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"密码错误(至少包含数字和字母,长度6~20位)\"}}","数字+字母+特殊字符密码长度小于6位"},
                {new RegisterBean(DEVICE_ID,MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(DEVICE_ID,MOBILE),"111ac111ac111ac111ac1"),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"密码错误(至少包含数字和字母,长度6~20位)\"}}","数字+字母密码长度大于20位"},
                {new RegisterBean(DEVICE_ID,MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(DEVICE_ID,MOBILE),"111%%111ac111ac111ac1"),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"密码错误(至少包含数字和字母,长度6~20位)\"}}","数字+字母+特殊字符密码长度大于20位"},
                {new RegisterBean(DEVICE_ID,MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(DEVICE_ID,MOBILE),PWD),"{\"result\":\"success\",\"code\":200}","正常数据，注册成功"},
        };
    }


    @DataProvider
    public Object[][] login(){
        return new Object[][]{
                {new RegisterBean(DEVICE_ID,LOGIN_MOBILE,null,null,LOGIN_PWD),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"设备ID获取失败\"}}","登录设备ID和手机号注册时设备ID不一致"},
                {new RegisterBean(DEVICE_ID,LOGIN_MOBILE,null,null,"test123"),"{\"status\":500,\"error\":{\"code\":106002,\"msg\":\"密码错误\"}}","密码错误"},
                {new RegisterBean(DEVICE_ID,LOGIN_MOBILE,null,null,null),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"缺少password\"}}","密码为空"},
                {new RegisterBean(DEVICE_ID,null,null,null,LOGIN_PWD),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"缺少mobile\"}}","手机号为空"},
                {new RegisterBean(DEVICE_ID,"13100001113",null,null,LOGIN_PWD),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"设备ID获取失败\"}}","手机号与设备ID不匹配"},
                {new RegisterBean(DEVICE_ID,"1575624025",null,null,LOGIN_PWD),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"手机号码格式错误\"}}","手机号格式错误"},
                {new RegisterBean(DEVICE_ID,LOGIN_MOBILE,null,null,LOGIN_PWD),"result","正常数据，登录成功"},
        };
    }

    @DataProvider
    public Object[][] resetTradePwd() throws Exception {
        return new Object[][]{
                {new RegisterBean(null,LOGIN_MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(DEVICE_ID,LOGIN_MOBILE),PWD),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"缺少deviceId\"}}","设备ID为空"},
                {new RegisterBean(DEVICE_ID,null,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(DEVICE_ID,LOGIN_MOBILE),PWD),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"缺少mobile\"}}","手机号为空"},
                {new RegisterBean(DEVICE_ID,LOGIN_MOBILE,null, VerifyCode.getMessageCode(DEVICE_ID,LOGIN_MOBILE),PWD),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"缺少graphicCode\"}}","图形验证码为空"},
                {new RegisterBean(DEVICE_ID,LOGIN_MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(DEVICE_ID,LOGIN_MOBILE),PWD),"{\"status\":500,\"error\":{\"code\":107004,\"msg\":\"图形验证码错误\"}}","图形验证码错误"},
                {new RegisterBean(DEVICE_ID,LOGIN_MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), null,PWD),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"缺少messageCode\"}}","短信验证码为空"},
                {new RegisterBean(DEVICE_ID,LOGIN_MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(DEVICE_ID,LOGIN_MOBILE),PWD),"{\"status\":500,\"error\":{\"code\":107004,\"msg\":\"短信验证码错误\"}}","短信验证码错误"},
                {new RegisterBean(DEVICE_ID,LOGIN_MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(DEVICE_ID,LOGIN_MOBILE),null),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"缺少password\"}}","密码为空"},
                {new RegisterBean(DEVICE_ID,MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(DEVICE_ID,MOBILE),"111111"),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"密码错误(至少包含数字和字母,长度6~20位)\"}}","纯数字密码长度小于6位"},
                {new RegisterBean(DEVICE_ID,MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(DEVICE_ID,MOBILE),"1111111"),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"密码错误(至少包含数字和字母,长度6~20位)\"}}","纯数字密码长度等于6位"},
                {new RegisterBean(DEVICE_ID,MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(DEVICE_ID,MOBILE),"111111111111111111111111"),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"密码错误(至少包含数字和字母,长度6~20位)\"}}","纯数字密码长度大于20位"},
                {new RegisterBean(DEVICE_ID,MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(DEVICE_ID,MOBILE),"abcabc"),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"密码错误(至少包含数字和字母,长度6~20位)\"}}","纯字母密码长度小于6位"},
                {new RegisterBean(DEVICE_ID,MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(DEVICE_ID,MOBILE),"abcabcd"),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"密码错误(至少包含数字和字母,长度6~20位)\"}}","纯字母密码长度等于6位"},
                {new RegisterBean(DEVICE_ID,MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(DEVICE_ID,MOBILE),"abcabcdabcabcdabcabcdabc"),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"密码错误(至少包含数字和字母,长度6~20位)\"}}","纯字母密码长度大于20位"},
                {new RegisterBean(DEVICE_ID,MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(DEVICE_ID,MOBILE),"111ac"),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"密码错误(至少包含数字和字母,长度6~20位)\"}}","数字+字母组合密码长度小于6位"},
                {new RegisterBean(DEVICE_ID,MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(DEVICE_ID,MOBILE),"1#$ac"),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"密码错误(至少包含数字和字母,长度6~20位)\"}}","数字+字母+特殊字符密码长度小于6位"},
                {new RegisterBean(DEVICE_ID,MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(DEVICE_ID,MOBILE),"111ac111ac111ac111ac1"),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"密码错误(至少包含数字和字母,长度6~20位)\"}}","数字+字母密码长度大于20位"},
                {new RegisterBean(DEVICE_ID,MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(DEVICE_ID,MOBILE),"111%%111ac111ac111ac1"),"{\"status\":422,\"error\":{\"code\":422,\"msg\":\"密码错误(至少包含数字和字母,长度6~20位)\"}}","数字+字母+特殊字符密码长度大于20位"},
                {new RegisterBean(DEVICE_ID,MOBILE,VerifyCode.getGraphicCode(DEVICE_ID), VerifyCode.getMessageCode(DEVICE_ID,MOBILE),"1qaz2wsx"),"{\"result\":\"success\",\"code\":200}","正常数据，密码重置成功"}

        };
    }
}
