package appName.frontend.listeners;

import appName.frontend.config.ConfigReader;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {

    public static void captureScreenshot(WebDriver driver, String testName, String instanceName, String methodName) throws IOException {
        String screenshotDir = ConfigReader.getProperty("screenshot.dir");
        String screenshotRelDir = ConfigReader.getProperty("screenshot.relDir");

        String fullyQualifiedFileName = instanceName + "." + methodName;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
        String timeStamp = LocalDateTime.now().format(dateFormatter);
        String filename = String.format("%s_%s",timeStamp,fullyQualifiedFileName);

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        String screenshotPath = screenshotDir + filename + ".png";

        //Ensure the directory exist, create it if not
        Path screenshotDirPath = Paths.get(screenshotPath).getParent();
        Files.createDirectories(screenshotDirPath);

        try{
            Files.copy(screenshot.toPath(), new File(screenshotPath).toPath());
        } catch (IOException e){
            e.printStackTrace();
        }

        //Attach the screenshot to the Extent report
        String screenshotRelPath = screenshotRelDir + filename + ".png";
        ExtentManager.getTest().fail("Test failed, see screenshot below",
                MediaEntityBuilder.createScreenCaptureFromPath(screenshotRelPath).build());
    }
}
