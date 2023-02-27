package ru.mcdev.integration.testUtils;

import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.mcdev.utils.HibernateUtil;
import ru.mcdev.utils.PropertiesUtil;

import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.DIALECT;
import static org.hibernate.cfg.AvailableSettings.DRIVER;
import static org.hibernate.cfg.AvailableSettings.FORMAT_SQL;
import static org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO;
import static org.hibernate.cfg.AvailableSettings.PASS;
import static org.hibernate.cfg.AvailableSettings.SHOW_SQL;
import static org.hibernate.cfg.AvailableSettings.URL;
import static org.hibernate.cfg.AvailableSettings.USER;

@UtilityClass
public class HibernateTestUtil {

    private final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14");

    static {
        postgres.start();
    }


    public SessionFactory buildSessionFactory() {

        Configuration configuration = HibernateUtil.buildConfiguration();
        configuration.setProperties(buildProperties());

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        return configuration.buildSessionFactory(serviceRegistry);
    }

    public Properties buildProperties() {

        Properties properties = new Properties();
        properties.put(URL, postgres.getJdbcUrl());
        properties.put(USER, postgres.getUsername());
        properties.put(PASS, postgres.getPassword());
        properties.put(DRIVER, PropertiesUtil.get(DRIVER));
        properties.put(DIALECT, PropertiesUtil.get(DIALECT));
        properties.put(SHOW_SQL, PropertiesUtil.get(SHOW_SQL));
        properties.put(FORMAT_SQL, PropertiesUtil.get(FORMAT_SQL));
        properties.put(HBM2DDL_AUTO, PropertiesUtil.get(HBM2DDL_AUTO));
        return properties;
    }

}
