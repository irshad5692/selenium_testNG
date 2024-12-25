package appName.frontend.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties = new Properties();

    static{
        try(FileInputStream inputStream = new FileInputStream("src/main/resources/config.properties")){
            properties.load(inputStream);
        } catch(IOException e){
            e.printStackTrace();
            throw new RuntimeException("Failed to load configuration properties");
        }
    }

    public static String getProperty(String key){
        return properties.getProperty(key);
    }
}
