package ru.mcdev.utils;

import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.mcdev.entity.Car;
import ru.mcdev.entity.Dispatcher;
import ru.mcdev.entity.Driver;
import ru.mcdev.entity.Employee;
import ru.mcdev.entity.Trip;

import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.DRIVER;
import static org.hibernate.cfg.Environment.DIALECT;
import static org.hibernate.cfg.Environment.FORMAT_SQL;
import static org.hibernate.cfg.Environment.HBM2DDL_AUTO;
import static org.hibernate.cfg.Environment.PASS;
import static org.hibernate.cfg.Environment.SHOW_SQL;
import static org.hibernate.cfg.Environment.URL;
import static org.hibernate.cfg.Environment.USER;

@UtilityClass
public class HibernateUtil {

    private SessionFactory sessionFactory;

    public SessionFactory buildSessionFactory() {

        if (sessionFactory == null) {

            try {
                var configuration = buildConfiguration();
                configuration.setProperties(buildProperties());

                var serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public Configuration buildConfiguration() {

        var configuration = new Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.addAnnotatedClass(Employee.class);
        configuration.addAnnotatedClass(Dispatcher.class);
        configuration.addAnnotatedClass(Driver.class);
        configuration.addAnnotatedClass(Car.class);
        configuration.addAnnotatedClass(Trip.class);
        return configuration;
    }

    public Properties buildProperties() {

        var properties = new Properties();
        properties.put(DRIVER, PropertiesUtil.get(DRIVER));
        properties.put(URL, PropertiesUtil.get(URL));
        properties.put(USER, PropertiesUtil.get(USER));
        properties.put(PASS, PropertiesUtil.get(PASS));
        properties.put(DIALECT, PropertiesUtil.get(DIALECT));
        properties.put(SHOW_SQL, PropertiesUtil.get(SHOW_SQL));
        properties.put(FORMAT_SQL, PropertiesUtil.get(FORMAT_SQL));
        properties.put(HBM2DDL_AUTO, PropertiesUtil.get(HBM2DDL_AUTO));
        return properties;
    }


}

