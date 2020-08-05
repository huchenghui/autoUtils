package listeners;

import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;

public class AllureReportListener  implements IHookable {
    @Override
    public void run(IHookCallBack iHookCallBack, ITestResult iTestResult) {
        iHookCallBack.runTestMethod(iTestResult);
        if (iTestResult.getThrowable() != null){
//            try {
//                Allure.addAttachment("失败截图 >> ",new FileInputStream(WebUtils.getScreenShotAs()));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

}