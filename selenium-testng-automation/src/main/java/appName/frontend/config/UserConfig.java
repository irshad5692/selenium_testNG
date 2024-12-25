package appName.frontend.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.testng.annotations.DataProvider;

public class UserConfig {

    private static final Map<String, User> users = new HashMap<>();

    private static Properties properties = new Properties();

    @DataProvider(name="TestUsers")
    public Object[][] TestUsers(){
        return new Object[][]{
            {"standard_user"},
            {"problem_user"},
        };
    }

    //Load properties from config file
    static {
        try(FileInputStream fis = new FileInputStream("src/main/resources/config.properties")){
            properties.load(fis);
        }catch (IOException e){
            e.printStackTrace();
            throw  new RuntimeException("Failed to load config.properties");
        }
    }

    //Method to fetch a user's data by key
    public static User getUser(String usernameKey){
        String name = properties.getProperty("user." + usernameKey + ".name");
        String surname = properties.getProperty("user." + usernameKey + ".surname");
        String username = properties.getProperty("user." + usernameKey + ".username");
        String password = properties.getProperty("user." + usernameKey + ".password");
        String enumValue = properties.getProperty("user." + usernameKey + ".enumValue");

        if(username != null && password != null){
            return  new User(name, surname, username, password, enumValue);
        }else {
            return null;
        }
    }

    //Inner class to hold user data (username, password)
    public static class User{
        private String name;
        private String surname;
        private String username;
        private String password;
        private String enumValue;

        public User(String name, String surname, String username, String password, String enumValue) {
            this.name = name;
            this.surname = surname;
            this.username = username;
            this.password = password;
            this.enumValue = enumValue;
        }

        public String getName() {
            return name;
        }
        public String getSurname() {
            return surname;
        }
        public String getUsername() {
            return username;
        }
        public String getPassword() {
            return password;
        }
        public String getEnumValue() {
            return enumValue;
        }
    }
}
