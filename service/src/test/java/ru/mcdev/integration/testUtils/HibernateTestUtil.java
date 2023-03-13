package ru.mcdev.integration.testUtils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.mcdev.utils.HibernateUtil;

import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.PASS;
import static org.hibernate.cfg.AvailableSettings.URL;
import static org.hibernate.cfg.AvailableSettings.USER;

@UtilityClass
public class HibernateTestUtil {

    private final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14");

    static {
        postgres.start();
    }

    public SessionFactory buildSessionFactory() {
        return HibernateUtil.buildConfiguration().addProperties(buildProperties()).buildSessionFactory();
    }

    @SneakyThrows
    public Properties buildProperties() {
        Properties properties = new Properties();
        properties.load(HibernateTestUtil.class.getClassLoader().getResourceAsStream("application-test.properties"));
        properties.put(URL, postgres.getJdbcUrl());
        properties.put(USER, postgres.getUsername());
        properties.put(PASS, postgres.getPassword());
        return properties;
    }

}
