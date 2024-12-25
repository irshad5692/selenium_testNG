package appName.frontend.pages;

import appName.frontend.config.UserConfig;
import appName.frontend.utils.WebDriverMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriver driver;
    private WebDriverMethods webDriverMethods;

    //Constructor accepts the driver and initializes page elements
    public LoginPage(WebDriver driver){
        this.driver=driver;
        this.webDriverMethods=new WebDriverMethods(driver);
        PageFactory.initElements(driver,this);
    }

    //Locators
    private final By usernameField = By.cssSelector("#user-name");
    private final By passwordField = By.cssSelector("#password");
    private final By loginButton = By.cssSelector("#login-button");


    public void login(UserConfig.User userData){
        enterUserName(userData.getUsername());
        enterPassword(userData.getPassword());
        clickSignInButton();
    }

    public void enterUserName(String userName){
        webDriverMethods.enterText(usernameField, userName);
    }

    public void enterPassword(String password){
        webDriverMethods.enterText(passwordField, password);
    }

    public void clickSignInButton(){
        webDriverMethods.click(loginButton);
    }



}
