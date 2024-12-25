package appName.frontend.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverMethods {
    private WebDriver driver;
    private WebDriverWait webDriverWait;

    public WebDriverMethods(WebDriver driver){
        this.driver = driver;
        this.webDriverWait = new WebDriverWait(driver,120);
    }

    //Getter method for WebDriverWait
    public WebDriverWait getWebDriverWait(){
        return webDriverWait;
    }

    public WebElement findElement(By elementLocator){
        return driver.findElement(elementLocator);
    }

    public void enterText(By elementLocator, String keysToSend){
        driver.findElement(elementLocator).clear();
        driver.findElement(elementLocator).sendKeys(keysToSend);
    }

    public void click(By elementLocator){
        driver.findElement(elementLocator).click();
    }

}
