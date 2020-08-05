package util;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;

public class AllureTest {

    public static void main(String[] args) throws Exception {
//        FileUtils ddt = new FileUtils("D:\\ddt\\dy_mini_program.xlsx",0);
//        String d = Encrypt.doEncrypt(ddt.readLine(2).get("parameter_data").toString());
//        HttpUtils.analysis(HttpUtils.doPost(EnumHttp.API_DOMAIN.getVal() + "/auth/admin",d,null));
        Object[][] o = HttpUtils.dataProvider("D:\\ddt\\dy_mini_program.xlsx","修改密码");
        for (Object[] obj : o){
            System.out.println(Arrays.toString(obj));
        }

    }

    @Test(dataProvider = "login")
    public void t(String rate){
        System.out.println("rate is >> " + rate);

    }

    @DataProvider(name = "login")
    public Object[][] dataProvider() throws Exception {
        return HttpUtils.dataProvider("D:\\ddt\\dy_mini_program.xlsx","中奖概率配置");
    }


}
