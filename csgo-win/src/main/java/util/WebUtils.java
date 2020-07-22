package util;

import config.EnumWebDriver;
import mapper.IElements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.List;

public class WebUtils {

    private static WebDriver driver;
    /**
     * param iElements 完成IElements接口的枚举
     * **/

//    public WebDriver driver = null;
    private WebDriverWait wait = null;
    public Robot robot = null;

    public WebUtils(String url) throws AWTException {
        System.setProperty("webdriver.chrome.driver", EnumWebDriver.WEBDRIVER_PATH.getVal());
        if (driver == null){
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.get(url);
            this.wait = new WebDriverWait(driver,30);
            this.robot = new Robot();
        }
    }

    /**
     * 定位单个元素
     * **/
    public WebElement findByCss(IElements iElements){
        By by = By.cssSelector(iElements.getElementPath());
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
        return driver.findElement(by);
    }

    /**
     * 定位一组元素
     * **/
    public List<WebElement> findsByCss(IElements iElements){
        By by = By.cssSelector(iElements.getElementPath());
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
        return driver.findElements(by);
    }

    /**
     * 向文本框输入数据
     * @param val 需要输入的数据
     * **/
    public void sendKeys(IElements iElements,String val){
        WebElement el = findByCss(iElements);
        el.clear();
        el.sendKeys(val);
    }

    /**
     * 键盘操作下拉框的方法
     * @param dropNo 需要发送几次VK_DOWN事件
     * **/
    public void selectTag(IElements iElements,int dropNo){
        findByCss(iElements).click();
        for (int i = 0;i < dropNo;i++){
            keyDown();
        }
        keyEnter();
    }

    /**
     * 键盘操作下拉框的方法
     * @param dropNo 需要发送几次VK_DOWN事件
     * @param rightNo 需要发送几次VK_RIGHT事件
     * **/
    public void selectTag(IElements iElements,int dropNo,int rightNo){
        findByCss(iElements).click();
        for (int i = 0;i < dropNo;i++){
            keyDown();
        }
        for (int right = 0;right < rightNo;right++){
            keyRight();
        }
        keyEnter();
    }

    public static WebDriver getDriver(){
        return driver;
    }

    public static File getScreenShotAs(String filePath){
        File screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        File target = new File(filePath);
        int len;
        byte[] c = new byte[1024];
        try (
                InputStream inputStream = new FileInputStream(screen);
                OutputStream outputStream = new FileOutputStream(target);
        ) {
            while ((len = (inputStream.read(c))) != -1) {
                outputStream.write(c,0,len);
                outputStream.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return target;
    }

    public static String replay(String resource){
        String[] noContains = new String[]{"\\","/",":","?","\"","<",">","|"};
        char[] c = resource.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0;i < c.length;i++){
            for (String str : noContains){
                if (str.equals(String.valueOf(c[i]))){
                    c[i] = '-';
                }
            }
        }
        for (char chars : c){
            builder.append(chars);
        }
        return builder.toString();
    }

    private void keyDown(){
        threadWait();
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
    }

    private void keyEnter(){
        threadWait();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    private void keyRight(){
        threadWait();
        robot.keyPress(KeyEvent.VK_RIGHT);
        robot.keyRelease(KeyEvent.VK_RIGHT);
    }

    private void threadWait(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
