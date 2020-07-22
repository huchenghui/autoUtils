package util;

import listeners.Listener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.awt.*;

@Listeners({Listener.class})
public class AllureTest {

    @Test
    public void t(){
        try {
            WebUtils webUtils = new WebUtils("http://www.tianmao.com");
        } catch (AWTException e) {
            e.printStackTrace();
        }
        Assert.fail();
    }


}
