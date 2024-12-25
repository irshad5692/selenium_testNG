package appName.frontend.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private ExtentTest test;
    private WebDriver driver;

    public void setWebDrier(WebDriver driver){
        this.driver = driver;
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Test suite started: "+context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test suite ended: "+context.getName());
        ExtentManager.flushReports();
    }

    @Override
    public void onTestStart(ITestResult result){
        //Retrieve test name and test description
//        String username = (String) result.getParameters()[0];   //get username from parameters
        String username = "user1";
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription() + "--" + username;

        //Get browser name from the parameters
        String browser = (String) result.getTestContext().getAttribute("browser");

        //Create a new test in extent reports with the name and description
        test = ExtentManager.createTest(testName,description);

        //Add the browser as a category to the test
        test.assignCategory(browser);

    }

    @Override
    public void onTestSuccess(ITestResult result){
        //log success to the report
        test.log(Status.PASS,"Test passed: "+result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result){
        test.log(Status.FAIL,"Test failed: "+result.getMethod().getMethodName());
        test.log(Status.FAIL, result.getThrowable()); //Log the exception that caused failure
        if (driver!=null){
            //optionally, capture screenshot here if needed
        }
    }

    @Override
    public void onTestSkipped(ITestResult result){
        test.log(Status.SKIP,"Test skipped: "+result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result){
        //Optional
    }
}
