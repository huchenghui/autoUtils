package util;

import config.EnumWebDriver;
import imp.EnumLogin;
import mapper.IElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class WebUtils {

    public static WebDriver driver = null;
    private static WebDriverWait wait = null;

    public static void launchDriver(String url){
        System.setProperty("webdriver.chrome.driver", EnumWebDriver.WEBDRIVER_PATH.getVal());
        if (driver == null){
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            wait = new WebDriverWait(driver,30);
            driver.get(url);
        }
    }

    public static WebElement findByCss(IElements iElements){
        return iElements.findBy(wait,driver);
    }

    public static List<WebElement> findsByCss(IElements iElements){
        return iElements.findsBy(wait,driver);
    }

    public static void sendKeys(IElements iElements,String val){
        findByCss(iElements).clear();
        findByCss(iElements).sendKeys(val);
    }



    public static void main(String[] args) {
        launchDriver("http://23.102.239.43:8000/csbamp/#/admin/login?redirect=%2Fadmin%2Forder%2FManual");
        sendKeys(EnumLogin.USER_NAME,"admin");
        sendKeys(EnumLogin.PASSWORD,"123456");
        findByCss(EnumLogin.LOGIN_BTN).click();
    }
}
