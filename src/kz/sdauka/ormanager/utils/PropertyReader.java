package kz.sdauka.ormanager.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Dauletkhan on 11.01.2015.
 */
public class PropertyReader {
    private final Logger LOG = Logger.getLogger(PropertyReader.class);
    private Properties properties = new Properties();
    public static final String SETTINGS_PROPERTIES = "settings.properties";

    public PropertyReader(String fileName) {
        InputStream inputStream = PropertyReader.class.getClassLoader().getResourceAsStream(fileName);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            LOG.error(" Property Exception" + e);
        }
    }

    public String getProperties(String key) {
        return properties.getProperty(key);
    }

    public void setProperties(String key, String value) {
        properties.setProperty(key, value);
    }
}
