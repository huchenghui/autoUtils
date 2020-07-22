package bean;

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
        return new Object[][]{
                {new PrizeConfigBean("com","",6,"奖品001",20,"PRIZE","15756240257"),"奖品001"},
                {new PrizeConfigBean("rate err","",7,"奖品002","30","FAKE","15756240257"),null},
                {new PrizeConfigBean("location err","","7","奖品002",30,"FAKE","15756240257"),null},
                {new PrizeConfigBean("type err","",7,"奖品002",30,"non","15756240257"),null},
                {new PrizeConfigBean("location err","",10,"奖品002",30,"TIMES","15756240257"),null},
                {new PrizeConfigBean("location err","",-10,"奖品002",30,"TIMES","15756240257"),null},
                {new PrizeConfigBean("location err","",5,"奖品002",30,"TIMES","15756240257"),null},
                {new PrizeConfigBean("rate err","",6,"奖品002",101,"TIMES","15756240257"),null},
                {new PrizeConfigBean("rate err","",4,"奖品002",-10,"TIMES","15756240257"),null},
                {new PrizeConfigBean("","",2,"rate大于0时command不能为空",10,"TIMES","15756240257"),null},
                {new PrizeConfigBean("WECHAT","",3,"rate大于0时wechat不能为空",10,"TIMES",""),null}
        };
    }

}
