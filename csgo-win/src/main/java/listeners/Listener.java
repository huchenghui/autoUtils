package listeners;

import config.EnumWebDriver;
import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import util.WebUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Listener implements ITestListener {

    private final Logger log = LoggerFactory.getLogger(Listener.class);
    @Override
    public void onTestStart(ITestResult iTestResult) {

    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String filePath = WebUtils.replay(WebUtils.getDriver().getTitle());
        StringBuilder builder = new StringBuilder(EnumWebDriver.SCREEN_SHOT_AS.getVal());
        builder.append(format.format(new Date()));
        builder.append(filePath);
        builder.append(".png");
        WebUtils.getScreenShotAs(builder.toString());
        try {
            log.info("图片地址 >> {}",builder.toString());
            Allure.addAttachment("失败自动截图 >>> ",new FileInputStream(builder.toString()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }
}
