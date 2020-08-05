package bean;

import config.EnumHttp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.testng.annotations.DataProvider;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrizeConfigBean {

    private Object command;
    private Object img;
    private Object location;
    private Object name;
    private Object rate;
    private Object type;
    private Object wechat;

    @DataProvider(name = "prizeBean")
    public Object[][] data(){
        String img = EnumHttp.IMG_PATH.getVal();
        return new Object[][]{
                {new PrizeConfigBean("com",img ,6,"奖品001",20,"PRIZE","15756240257"),"奖品001"},
                {new PrizeConfigBean("",img,7,"暗号不能为空","30","PRIZE","15756240257"),null},
                {new PrizeConfigBean("com",null,7,"图片不能为空","30","PRIZE","15756240257"),null},
                {new PrizeConfigBean("奖品名称不能为空",img,7,"","30","PRIZE","15756240257"),null},
                {new PrizeConfigBean("com",img,7,"领奖微信不能为空","30","PRIZE",""),null},
                {new PrizeConfigBean("location err",img,"7","location7",30,"FAKE","15756240257"),null},
                {new PrizeConfigBean("type err",img,7,"奖品类型错误",30,"non","15756240257"),null},
                {new PrizeConfigBean("location err",img,10,"奖品002",30,"TIMES","15756240257"),null},
                {new PrizeConfigBean("location err",img,-10,"奖品002",30,"TIMES","15756240257"),null},
                {new PrizeConfigBean("location err",img,5,"奖品002",30,"TIMES","15756240257"),null},

        };
    }

}
