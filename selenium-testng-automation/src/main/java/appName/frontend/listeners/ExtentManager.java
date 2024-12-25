package appName.frontend.listeners;

import appName.frontend.config.ConfigReader;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExtentManager {

    private static ExtentReports extent;

    private static ExtentHtmlReporter htmlReporter;

    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static ExtentReports getExtentReports(){
        if(extent == null){
            System.out.println("Initializing ExtentReports");
            initializeExtentReports();
        }
        return extent;
    }

    //Initialize ExtentReports for version 3.x
    private static void initializeExtentReports(){
        try{
            String reportFileName = ConfigReader.getProperty("report.fileName");
            String reportDir = ConfigReader.getProperty("report.dir");
            String reportPath = reportDir + reportFileName;

//            Ensure the directory exist, create if if not exist
            Path reportDirPath = Paths.get(reportPath).getParent();
            Files.createDirectories(reportDirPath);

            //Initialize ExtentHtmlReporter
            htmlReporter = new ExtentHtmlReporter(reportPath);
            htmlReporter.config().setTheme(Theme.STANDARD);
            htmlReporter.config().setDocumentTitle("Automation test report");
            htmlReporter.config().setReportName("Test results");
            htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);

            //Initialize extentreports and attach html reporter
            extent= new ExtentReports();
            extent.attachReporter(htmlReporter);

            //Set additional system info
            extent.setSystemInfo("OS",System.getProperty("os.name"));
            extent.setSystemInfo("User", System.getProperty("user.name"));
            extent.setSystemInfo("Framework architect","Irshad Telsang");

            System.out.println("ExtentReports initialized successfully");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error initializing ExtentReports"+e.getMessage());
        }
    }

    //Create and assigns an extent test instance for the current test
    public static synchronized ExtentTest createTest(String testName, String description){
        ExtentTest extentTest = getExtentReports().createTest(testName, description);
        test.set(extentTest);
        return extentTest;
    }

    public static ExtentTest getTest(){
        return test.get();
    }

    //flush the report at the end of test suite
    public static void flushReports(){
        if(extent != null){
            extent.flush();
        }
    }
}
