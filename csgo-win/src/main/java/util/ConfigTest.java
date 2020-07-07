package util;

import java.io.IOException;

public class ConfigTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        FileUtils utils = new FileUtils("d:\\title.xlsx",0);
        System.out.println(utils.read(1,0));
        utils.close();
    }

}
