package ru.mcdev;

import ru.mcdev.entity.Car;
import ru.mcdev.entity.Dispatcher;
import ru.mcdev.entity.Driver;
import ru.mcdev.entity.Trip;
import ru.mcdev.entity.enums.Role;
import ru.mcdev.utils.HibernateUtil;

public class App {

    public static void main(String[] args) {

        var dispatcher = Dispatcher.builder().email("dispatcher1@gmail.com").role(Role.DISPATCHER).build();
        var driver = Driver.builder().email("driver1@gmail.com").role(Role.DRIVER).build();
        var car = Car.builder().registrationNumber("1").build();
        var trip = Trip.builder().driver(driver).car(car).dispatcher(dispatcher).build();

        try (var session = HibernateUtil.buildSessionFactory().openSession()) {
                session.beginTransaction();
                session.save(dispatcher);
                session.save(driver);
                session.save(car);
                session.save(trip);
                session.getTransaction().commit();
        }
    }
}
