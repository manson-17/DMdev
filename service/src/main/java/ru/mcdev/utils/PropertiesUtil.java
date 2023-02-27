package ru.mcdev.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.util.Properties;

@UtilityClass
public class PropertiesUtil {

    private final Properties properties = new Properties();

    static {
        loadProperties();
    }

    @SneakyThrows
    private void loadProperties() {
        try (var inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(inputStream);
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }
}
