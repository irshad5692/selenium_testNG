package appName.frontend.tests.login;

import appName.frontend.config.UserConfig;
import appName.frontend.pages.LoginPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import appName.frontend.container.BaseTest;

public class ValidLoginTests extends BaseTest{

    private static final Logger log = LoggerFactory.getLogger(ValidLoginTests.class);
    private LoginPage loginPage;

    @BeforeMethod
    @Override
    public void setUp(){
        //Call the parent class 'setUp' method to initialize webdriver first
        super.setUp();  //This ensures the driver is setup before running each test
        loginPage = new LoginPage(driver);
    }

	@Test(dataProvider = "TestUsers", dataProviderClass = UserConfig.class,
            description = "Login with user data provided from TestNG data provider")
    public void loginTest1(@Optional("standard_user") String loginUser){
        UserConfig.User userData = UserConfig.getUser(loginUser);
        loginPage.login(userData);
    }

    @Test(description = "Login with direct user name provided")
    public void loginTest2(){
        UserConfig.User userData = UserConfig.getUser("standard_user");
        System.out.println(userData.getEnumValue());
        System.out.println(userData.getName());
        System.out.println(userData.getSurname());
        System.out.println(userData.getUsername());
        System.out.println(userData.getPassword());
        loginPage.login(userData);
    }

    @Test(description = "Reading data from UserConfig utility")
    public void loginTest3(){
        UserConfig.User userData = UserConfig.getUser("standard_user");

        //This will fetch the data for "standard_user"
        System.out.println(userData.getEnumValue());
        System.out.println(userData.getName());
        System.out.println(userData.getSurname());
        System.out.println(userData.getUsername());
        System.out.println(userData.getPassword());
    }
}
