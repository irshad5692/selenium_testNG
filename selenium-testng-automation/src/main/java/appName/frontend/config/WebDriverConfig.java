package appName.frontend.config;

import java.io.File;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.microsoft.edge.seleniumtools.EdgeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverConfig {

	public static WebDriver getDriver(String browser) {
		WebDriver driver;

		if (browser.equalsIgnoreCase("edge")) {
			return initEdgeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			return initFirefoxDriver();
		}
		return initChromeDriver();
	}

	private static WebDriver initChromeDriver() {
		WebDriverManager.chromedriver().setup();

		ChromeOptions options = new ChromeOptions();
//		options.addArguments("--headless=new");
		options.addArguments("--disable-gpu");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-extensions");

		// Set chrome preferences using a hashMap
		HashMap<String, Object> chromePreferences = new HashMap<>();
		chromePreferences.put("profile.password_manager_enabled", false);
		chromePreferences.put("profile.default_content_settings.popup", 0);
		chromePreferences.put("profile.default_content_settings_values.automatic_downloads", 1);
		chromePreferences.put("safebrowsing.enabled", false);
		chromePreferences.put("download.prompt_for_download", false);
		chromePreferences.put("download.default_directory", new File("target/tmp/downloads").getAbsolutePath());

		options.setExperimentalOption("prefs", chromePreferences);
		return new ChromeDriver(options);
	}

	private static WebDriver initEdgeDriver() {
		WebDriverManager.edgedriver().setup();

		EdgeOptions options = new EdgeOptions();
		options.addArguments("--headless");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-gpu");

		return new EdgeDriver(options);
	}

	private static WebDriver initFirefoxDriver() {
		WebDriverManager.edgedriver().setup();

		FirefoxOptions options = new FirefoxOptions();
		options.addArguments("--headless");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-gpu");

		return new FirefoxDriver(options);
	}

}
