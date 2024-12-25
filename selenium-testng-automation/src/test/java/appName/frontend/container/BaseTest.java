package appName.frontend.container;

import appName.frontend.config.ConfigReader;
import appName.frontend.listeners.ScreenshotUtil;
import appName.frontend.utils.Constants;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import appName.frontend.config.WebDriverConfig;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class BaseTest {

    protected WebDriver driver;

    public BaseTest(){
    }

    @BeforeClass
    @Parameters("browser")
    public void setUp(ITestContext context, @Optional("chrome") String browser){
        System.out.println("Before class initiated");

        //Set default value for browser if not provided in testng.xml
        if (browser == null || browser.isEmpty()){
            browser = "chrome";
        }

        //Set the browser in the TestNG context, which will be accessed in the listener
        context.setAttribute("browser", browser);

        //Initialize the Webdriver based on the selected browser
        this.driver = WebDriverConfig.getDriver(browser);
    }

    @AfterClass
    public void closeBrowser(){
        driver.quit();
    }

    @BeforeMethod
    public void setUp(){
        driver.manage().window().maximize();
        driver.get(ConfigReader.getProperty("app.url"));
    }

    @AfterMethod
    public void baseTearDown(ITestResult result) throws IOException {
        //Do cleanup of tmp/downloads folder after every method regardless of pass/fail
        cleanup();

        System.out.println("Tear down called for test:"+result.getMethod().getMethodName());
        if(!result.isSuccess()){
            System.out.println("Test failed, capturing screenshot..");
            String instanceName = result.getInstanceName();
            String methodName = result.getMethod().getMethodName();

            if(driver != null){
                try{
                    ScreenshotUtil.captureScreenshot(driver, result.getName(), instanceName, methodName);
                } catch (IOException e){
                    e.printStackTrace();
                }
            } else {
                System.out.println("Webdriver is null, skipping screenshot and log capture");
            }
        } else {
            System.out.println("Test passed, no tear down/screenshot");
        }

    }

    public static void cleanup() throws IOException {
        FileUtils.deleteDirectory(new File(Constants.DOWNLOAD_PATH));
    }

}
