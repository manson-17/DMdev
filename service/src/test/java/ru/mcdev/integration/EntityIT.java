package ru.mcdev.integration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.mcdev.entity.Car;
import ru.mcdev.entity.Dispatcher;
import ru.mcdev.entity.Driver;
import ru.mcdev.entity.Trip;
import ru.mcdev.entity.enums.Status;
import ru.mcdev.integration.testUtils.EntityUtil;
import ru.mcdev.integration.testUtils.HibernateTestUtil;

import static org.assertj.core.api.Assertions.assertThat;

public class EntityIT {

    private static SessionFactory sessionFactory;
    private Session session;

    @BeforeAll
    static void init() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();

    }

    @AfterAll
    static void closeConnection() {
        sessionFactory.close();
    }

    @BeforeEach
    void beginTransaction() {
        session = sessionFactory.openSession();
        session.beginTransaction();

    }

    @AfterEach
    void rollbackTransaction() {
        session.getTransaction().rollback();
        session.close();
    }

    @Nested
    class DispatcherTest {

        Dispatcher dispatcher = EntityUtil.createDispatcher();

        @Test
        public void shouldSave() {

            var actualResult = session.save(dispatcher);

            assertThat(actualResult).isNotNull();
        }

        @Test
        public void shouldFind() {

            session.save(dispatcher);
            session.clear();

            var actualResult = session.get(Dispatcher.class, dispatcher.getId());

            assertThat(actualResult).isNotNull().isEqualTo(dispatcher);
        }

        @Test
        void shouldUpdate() {

            session.save(dispatcher);
            session.clear();
            var dispatcher1 = session.get(Dispatcher.class, dispatcher.getId());
            dispatcher.setEmail("updateEmail@mail.com");

            session.update(dispatcher1);
            session.flush();
            session.clear();

            var actualResult = session.get(Dispatcher.class, dispatcher1.getId());
            assertThat(actualResult.getEmail()).isNotNull().isEqualTo(dispatcher1.getEmail());
        }

        @Test
        void shouldDelete() {

            session.save(dispatcher);
            session.clear();

            session.delete(dispatcher);
            session.flush();
            session.clear();

            var actualResult = session.get(Dispatcher.class, dispatcher.getId());
            assertThat(actualResult).isNull();
        }

    }

    @Nested
    class DriverTest {

        Driver driver = EntityUtil.createDriver();

        @Test
        void shouldSave() {

            var actualResult = session.save(driver);

            assertThat(actualResult).isNotNull();
        }

        @Test
        void shouldFind() {

            session.save(driver);
            session.clear();

            var actualResult = session.get(Driver.class, driver.getId());

            assertThat(actualResult).isNotNull().isEqualTo(driver);
        }

        @Test
        void shouldFindAll() {

            var driver1 = Driver.builder().email("driver1@mail.com").build();
            session.save(driver);
            session.save(driver1);
            session.clear();

            var actualResult = session.createQuery("from Driver", Driver.class).getResultList();

            assertThat(actualResult).isNotNull().hasSize(2).contains(driver, driver1);
        }

        @Test
        void shouldUpdate() {

            session.save(driver);
            session.clear();
            var driver1 = session.get(Driver.class, driver.getId());
            driver.setEmail("updateEmail@mail.com");

            session.update(driver1);
            session.flush();
            session.clear();

            var actualResult = session.get(Driver.class, driver1.getId());
            assertThat(actualResult.getEmail()).isNotNull().isEqualTo(driver1.getEmail());
        }

        @Test
        void shouldDelete() {

            session.save(driver);
            session.clear();

            session.delete(driver);
            session.flush();
            session.clear();

            var actualResult = session.get(Driver.class, driver.getId());
            assertThat(actualResult).isNull();
        }

    }

    @Nested
    class CarTest {

        Car car = EntityUtil.createCar();

        @Test
        void shouldSave() {

            var actualResult = session.save(car);

            assertThat(actualResult).isNotNull();
        }

        @Test
        void shouldFind() {

            session.save(car);
            session.clear();

            var actualResult = session.get(Car.class, car.getId());

            assertThat(actualResult).isNotNull().isEqualTo(car);
        }

        @Test
        void shouldUpdate() {

            session.save(car);
            session.clear();
            var car1 = session.get(Car.class, car.getId());
            car.setRegistrationNumber("2");

            session.update(car1);
            session.flush();
            session.clear();

            var actualResult = session.get(Car.class, car1.getId());
            assertThat(actualResult.getRegistrationNumber()).isNotNull().isEqualTo(car1.getRegistrationNumber());
        }

        @Test
        void shouldDelete() {

            session.save(car);
            session.clear();

            session.delete(car);
            session.flush();
            session.clear();

            var actualResult = session.get(Car.class, car.getId());
            assertThat(actualResult).isNull();
        }
    }

    @Nested
    class TripTest {

        @Test
        void shouldSave() {

            var car = EntityUtil.createCar();
            var driver = EntityUtil.createDriver();
            var dispatcher = EntityUtil.createDispatcher();
            var trip = Trip.builder().car(car).dispatcher(dispatcher).driver(driver).build();
            session.save(car);
            session.save(driver);
            session.save(dispatcher);

            var actualResult = session.save(trip);

            assertThat(actualResult).isNotNull();
        }

        @Test
        void shouldFind() {

            var car = EntityUtil.createCar();
            var driver = EntityUtil.createDriver();
            var dispatcher = EntityUtil.createDispatcher();
            var trip = Trip.builder().car(car).dispatcher(dispatcher).driver(driver).build();
            session.save(car);
            session.save(driver);
            session.save(dispatcher);
            session.save(trip);
            session.clear();

            var actualResult = session.get(Trip.class, trip.getId());

            assertThat(actualResult).isNotNull().isEqualTo(trip);
        }

        @Test
        void shouldUpdate() {

            var car = EntityUtil.createCar();
            var driver = EntityUtil.createDriver();
            var dispatcher = EntityUtil.createDispatcher();
            var trip = Trip.builder().car(car).dispatcher(dispatcher).driver(driver).build();
            session.save(car);
            session.save(driver);
            session.save(dispatcher);
            session.save(trip);
            session.clear();
            var trip1 = session.get(Trip.class, trip.getId());
            trip1.setStatus(Status.COMPLETED);

            session.update(trip1);
            session.flush();
            session.clear();

            var actualResult = session.get(Trip.class, trip1.getId());
            assertThat(actualResult.getStatus()).isNotNull().isEqualTo(trip1.getStatus());
        }

        @Test
        void shouldDelete() {

            var car = EntityUtil.createCar();
            var driver = EntityUtil.createDriver();
            var dispatcher = EntityUtil.createDispatcher();
            var trip = Trip.builder().car(car).dispatcher(dispatcher).driver(driver).build();
            session.save(car);
            session.save(driver);
            session.save(dispatcher);
            session.save(trip);
            session.clear();

            session.delete(trip);
            session.flush();
            session.clear();

            var actualResult = session.get(Trip.class, trip.getId());
            assertThat(actualResult).isNull();
        }
    }
}

