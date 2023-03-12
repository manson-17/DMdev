package ru.mcdev.integration.testUtils;

import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.mcdev.entity.Car;
import ru.mcdev.entity.Dispatcher;
import ru.mcdev.entity.Driver;
import ru.mcdev.entity.Trip;
import ru.mcdev.entity.enums.Category;
import ru.mcdev.entity.enums.Role;
import ru.mcdev.entity.enums.Status;

import java.math.BigDecimal;
import java.util.ArrayList;

@UtilityClass
public class TestDataImporter {

    public void importData(SessionFactory sessionFactory) {
        @Cleanup Session session = sessionFactory.openSession();

        var billGates = saveDriver(session, "email1@mail.com", "Bill", "Gates",
                Category.C, "000001");
        var steveJobs = saveDriver(session, "email2@mail.com", "Steve", "Jobs",
                Category.B, "000002");
        var sergeyBrin = saveDriver(session, "email3@mail.com", "Sergey", "Brin",
                Category.B, "000003");
        var timCook = saveDriver(session, "email4@mail.com", "Tim", "Cook",
                Category.C, "0000004");
        var dianeGreene = saveDriver(session, "email5@mail.com", "Diane", "Greene",
                Category.D, "000005");

        var fedorSumkin = saveDispatcher(session, "email6@mail.com", "Fedor", "Sumkin");
        var senyaGanjubas = saveDispatcher(session, "email7@mail.com", "Senya", "Ganjubas");

        var pontiac = saveCar(session, "Pontiac", "fiero", "000", Category.B);
        var pontiac1 = saveCar(session, "Pontiac", "fiero", "100", Category.B);
        var pontiac2 = saveCar(session, "Pontiac", "gto", "111", Category.C);
        var p601 = saveCar(session, "Trabant", "p601", "001", Category.C);

        saveTrip(session, billGates, fedorSumkin, pontiac, Status.COMPLETED, "ulica 1",
                "prospekt 26", BigDecimal.valueOf(50.00));
        saveTrip(session, billGates, senyaGanjubas, pontiac, Status.COMPLETED, "ulica 5",
                "prospekt 256", BigDecimal.valueOf(150.01));
        saveTrip(session, billGates, fedorSumkin, pontiac, Status.READY, "ulica 21",
                "prospekt 116", BigDecimal.valueOf(75.00));
        saveTrip(session, billGates, fedorSumkin, pontiac, Status.READY, "ulica 22",
                "prospekt 666", BigDecimal.valueOf(76.00));

        saveTrip(session, steveJobs, senyaGanjubas, p601, Status.COMPLETED, "ulica 1",
                "prospekt 26", BigDecimal.valueOf(45.00));
        saveTrip(session, steveJobs, senyaGanjubas, pontiac, Status.COMPLETED, "ulica 1",
                "prospekt 66", BigDecimal.valueOf(55.00));
        saveTrip(session, steveJobs, senyaGanjubas, p601, Status.COMPLETED, "ulica 8",
                "prospekt 26", BigDecimal.valueOf(50.00));

        saveTrip(session, timCook, fedorSumkin, p601, Status.COMPLETED, "ulica 89",
                "prospekt 26", BigDecimal.valueOf(36.00));
        saveTrip(session, timCook, fedorSumkin, p601, Status.READY, "prospekt 26",
                "ulica 89", BigDecimal.valueOf(89.00));

        saveTrip(session, sergeyBrin, fedorSumkin, pontiac, Status.COMPLETED, "ulica 15",
                "prospekt 155", BigDecimal.valueOf(56.00));
        saveTrip(session, sergeyBrin, fedorSumkin, pontiac, Status.COMPLETED, "ulica 15",
                "prospekt 155", BigDecimal.valueOf(68.00));
        saveTrip(session, sergeyBrin, fedorSumkin, pontiac, Status.COMPLETED, "ulica 15",
                "prospekt 155", BigDecimal.valueOf(38.00));

        saveTrip(session, dianeGreene, senyaGanjubas, pontiac, Status.COMPLETED, "prospekt 26",
                "prospekt 98", BigDecimal.valueOf(56.00));
        saveTrip(session, dianeGreene, senyaGanjubas, pontiac, Status.COMPLETED, "ulica 1",
                "prospekt 26", BigDecimal.valueOf(51.00));
        saveTrip(session, dianeGreene, fedorSumkin, pontiac, Status.READY, "ulica 1",
                "prospekt 26", BigDecimal.valueOf(74.00));
    }

    private static Driver saveDriver(Session session, String email, String firstName, String lastName,
                                     Category category, String rightsNumber) {
        var user = Driver.builder()
                .email(email)
                .firstname(firstName)
                .lastname(lastName)
                .password("qwerty")
                .role(Role.DRIVER)
                .category(category)
                .drivingRightsNumber(rightsNumber)
                .trips(new ArrayList<>())
                .build();
        session.save(user);
        return user;
    }

    private static Dispatcher saveDispatcher(Session session, String email, String firstName, String lastName) {
        var user = Dispatcher.builder()
                .email(email)
                .firstname(firstName)
                .lastname(lastName)
                .password("qwerty")
                .role(Role.DISPATCHER)
                .trips(new ArrayList<>())
                .build();
        session.save(user);
        return user;
    }

    private static Car saveCar(Session session, String brand, String model, String number, Category category) {
        var car = Car.builder()
                .brand(brand)
                .model(model)
                .category(category)
                .registrationNumber(number)
                .trips(new ArrayList<>())
                .build();
        session.save(car);
        return car;
    }

    private static void saveTrip(Session session, Driver driver, Dispatcher dispatcher, Car car, Status status,
                                 String placeOfDeparture, String destination, BigDecimal cost) {
        var trip = Trip.builder()
                .status(status)
                .placeOfDeparture(placeOfDeparture)
                .destination(destination)
                .cost(cost)
                .build();
        trip.addCar(car);
        trip.addDispatcher(dispatcher);
        trip.addDriver(driver);
        session.save(trip);
    }
}
