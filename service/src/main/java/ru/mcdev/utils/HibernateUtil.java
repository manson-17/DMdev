package ru.mcdev.utils;

import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;
import ru.mcdev.entity.Car;
import ru.mcdev.entity.Dispatcher;
import ru.mcdev.entity.Driver;
import ru.mcdev.entity.Employee;
import ru.mcdev.entity.Trip;

import java.io.IOException;
import java.util.Properties;

@UtilityClass
public class HibernateUtil {

    public SessionFactory buildSessionFactory() throws IOException {
        var properties = new Properties();
        properties.load(HibernateUtil.class.getClassLoader().getResourceAsStream("application.properties"));
        return buildConfiguration().addProperties(properties).buildSessionFactory();
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

}

