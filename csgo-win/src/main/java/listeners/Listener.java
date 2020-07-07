package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listener implements ITestListener {
    @Override
    public void onTestStart(ITestResult iTestResult) {

    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {

    }


    @Override
    public void onTestFailure(ITestResult iTestResult) {

//        File file = ((TakesScreenshot) ConfigTest.driver).getScreenshotAs(OutputType.FILE);
//        int len = -1;
//        byte[] c = new byte[1024];
//        try (
//                InputStream inputStream = new FileInputStream(file);
//                OutputStream outputStream = new FileOutputStream("D:\\1.png");
//        ) {
//            while ((len = (inputStream.read(c))) != -1) {
//                outputStream.write(c,0,len);
//                outputStream.flush();
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
