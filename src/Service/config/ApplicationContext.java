package Service.config;

import java.io.ObjectInputFilter;
import java.util.Properties;

public class ApplicationContext {
    private static final Properties PROPERTIES= Config.getProperties();
    public static Properties getProperties(){
        return PROPERTIES;
    }
}
