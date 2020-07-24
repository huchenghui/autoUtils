package bean;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.testng.annotations.DataProvider;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RateConfigBean {

    private String location;
    private Object rate;

    //location相同
    public String locationFit(){
        List<RateConfigBean> rate = new LinkedList<>();
        for (int i = 1;i <= 9;i++){
            if (i == 5){
                continue;
            }
            rate.add(new RateConfigBean("location1",10));
        }
        rate.get(rate.size()-1).setRate(30);
        return JSON.toJSONString(rate);
    }

    //概率小于100
    public String rateLessOneHundred(){
        List<RateConfigBean> rate = new LinkedList<>();
        for (int i = 1;i <= 9;i++){
            if (i == 5){
                continue;
            }
            rate.add(new RateConfigBean("location" + i,10));
        }

        return JSON.toJSONString(rate);
    }

    //概率大于100
    public String rateGreatThanOneHundred(){
        List<RateConfigBean> rate = new LinkedList<>();
        for (int i = 1;i <= 9;i++){
            if (i == 5){
                continue;
            }
            rate.add(new RateConfigBean("location" + i,20));
        }

        return JSON.toJSONString(rate);
    }

    //其中一个概率为负数
    public String rateNegative(){
        List<RateConfigBean> rate = new LinkedList<>();
        for (int i = 1;i <= 9;i++){
            if (i == 5){
                continue;
            }
            rate.add(new RateConfigBean("location" + i,10));
        }
        rate.get(rate.size()-2).setRate(-10);
        rate.get(rate.size()-1).setRate(50);
        return JSON.toJSONString(rate);
    }

    //rate包含小数
    public String rateDecimals(){
        List<RateConfigBean> rate = new LinkedList<>();
        for (int i = 1;i <= 9;i++){
            if (i == 5){
                continue;
            }
            rate.add(new RateConfigBean("location" + i,10));
        }
        rate.get(rate.size()-3).setRate(10.5);
        rate.get(rate.size()-2).setRate(9.5);
        rate.get(rate.size()-1).setRate(30);
        return JSON.toJSONString(rate);
    }

    public String rateAStr(){
        List<RateConfigBean> rate = new LinkedList<>();
        for (int i = 1;i <= 9;i++){
            if (i == 5){
                continue;
            }
            rate.add(new RateConfigBean("location" + i,10));
        }
        rate.get(rate.size()-3).setRate("test");
        rate.get(rate.size()-2).setRate(10);
        rate.get(rate.size()-1).setRate(40);
        return JSON.toJSONString(rate);
    }

    //正常数据
    public String normal(){
        List<RateConfigBean> rate = new LinkedList<>();
        for (int i = 1;i <= 9;i++){
            if (i == 5){
                continue;
            }
            rate.add(new RateConfigBean("location" + i,10));
        }

        rate.get(rate.size()-1).setRate(30);
        return JSON.toJSONString(rate);
    }


    @DataProvider(name = "rate")
    public Object[][] rateData(){
        return new Object[][]{
                {normal(),"8"},
                {rateDecimals(),"400"},
                {rateAStr(),"400"},
                {rateNegative(),"400"},
                {rateGreatThanOneHundred(),"400"},
                {rateLessOneHundred(),"400"},
                {locationFit(),"400"}
        };
    }

}
