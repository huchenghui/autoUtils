package util;

public class AllureTest {

    public static void main(String[] args) throws Exception {
        Object[][] obj = HttpUtils.dataProvider("d:\\ddt\\csgo_internet_bar_version.xlsx", 0, "设置交易连接");
        System.out.println(obj[0][0]);
        System.out.println(obj[0][1]);
//        for (Object[] o : obj){
//            System.out.println(Arrays.toString(o));
//        }
    }


}
