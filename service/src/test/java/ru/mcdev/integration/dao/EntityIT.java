package ru.mcdev.integration.dao;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.mcdev.entity.Car;
import ru.mcdev.entity.Dispatcher;
import ru.mcdev.entity.Driver;
import ru.mcdev.entity.Trip;
import ru.mcdev.entity.enums.Status;
import ru.mcdev.integration.testUtils.EntityUtil;

import static org.assertj.core.api.Assertions.assertThat;

class EntityIT extends BaseIntegrationTest {

    @Nested
    class DispatcherTest {

        Dispatcher dispatcher = EntityUtil.createDispatcher();

        @Test
        void shouldSave() {

            session.save(dispatcher);
            session.flush();
            session.clear();

            var actualResult = session.get(Dispatcher.class, dispatcher.getId());

            assertThat(actualResult).isEqualTo(dispatcher);
        }

        @Test
        void shouldFind() {

            session.save(dispatcher);
            session.flush();
            session.clear();

            var actualResult = session.get(Dispatcher.class, dispatcher.getId());

            assertThat(actualResult).isEqualTo(dispatcher);
        }

        @Test
        void shouldUpdate() {

            session.save(dispatcher);
            session.flush();
            session.clear();
            var dispatcher1 = session.get(Dispatcher.class, dispatcher.getId());
            dispatcher.setEmail("updateEmail@mail.com");

            session.update(dispatcher1);
            session.flush();
            session.clear();

            var actualResult = session.get(Dispatcher.class, dispatcher1.getId());
            assertThat(actualResult.getEmail()).isEqualTo(dispatcher1.getEmail());
        }

        @Test
        void shouldDelete() {

            session.save(dispatcher);
            session.flush();
            session.clear();
            var dispatcher1 = session.get(Dispatcher.class, dispatcher.getId());

            session.delete(dispatcher1);
            session.flush();
            session.clear();

            var actualResult = session.get(Dispatcher.class, dispatcher1.getId());
            assertThat(actualResult).isNull();
        }

    }

    @Nested
    class DriverTest {

        Driver driver = EntityUtil.createDriver();

        @Test
        void shouldSave() {

            session.save(driver);
            session.flush();
            session.clear();

            var actualResult = session.get(Driver.class, driver.getId());

            assertThat(actualResult).isEqualTo(driver);
        }

        @Test
        void shouldFind() {

            session.save(driver);
            session.flush();
            session.clear();

            var actualResult = session.get(Driver.class, driver.getId());

            assertThat(actualResult).isEqualTo(driver);
        }

        @Test
        void shouldUpdate() {

            session.save(driver);
            session.flush();
            session.clear();
            var driver1 = session.get(Driver.class, driver.getId());
            driver.setEmail("updateEmail@mail.com");

            session.update(driver1);
            session.flush();
            session.clear();

            var actualResult = session.get(Driver.class, driver1.getId());
            assertThat(actualResult.getEmail()).isEqualTo(driver1.getEmail());
        }

        @Test
        void shouldDelete() {

            session.save(driver);
            session.flush();
            session.clear();
            var driver1 = session.get(Driver.class, driver.getId());

            session.delete(driver1);
            session.flush();
            session.clear();

            var actualResult = session.get(Driver.class, driver1.getId());
            assertThat(actualResult).isNull();
        }

    }

    @Nested
    class CarTest {

        Car car = EntityUtil.createCar();

        @Test
        void shouldSave() {

            session.save(car);
            session.flush();
            session.clear();

            var actualResult = session.get(Car.class, car.getId());

            assertThat(actualResult).isEqualTo(car);
        }

        @Test
        void shouldFind() {

            session.save(car);
            session.flush();
            session.clear();

            var actualResult = session.get(Car.class, car.getId());

            assertThat(actualResult).isEqualTo(car);
        }

        @Test
        void shouldUpdate() {

            session.save(car);
            session.flush();
            session.clear();
            var car1 = session.get(Car.class, car.getId());
            car.setRegistrationNumber("2");

            session.update(car1);
            session.flush();
            session.clear();

            var actualResult = session.get(Car.class, car1.getId());
            assertThat(actualResult.getRegistrationNumber()).isEqualTo(car1.getRegistrationNumber());
        }

        @Test
        void shouldDelete() {

            session.save(car);
            session.flush();
            session.clear();
            var car1 = session.get(Car.class, car.getId());
            session.delete(car1);
            session.flush();
            session.clear();

            var actualResult = session.get(Car.class, car1.getId());
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
            session.save(trip);
            session.flush();
            session.clear();

            Trip actualResult = session.get(Trip.class, trip.getId());

            assertThat(actualResult).isEqualTo(trip);
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
            session.flush();
            session.clear();

            var actualResult = session.get(Trip.class, trip.getId());

            assertThat(actualResult).isEqualTo(trip);
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
            session.flush();
            session.clear();
            var trip1 = session.get(Trip.class, trip.getId());
            trip1.setStatus(Status.COMPLETED);

            session.update(trip1);
            session.flush();
            session.clear();

            var actualResult = session.get(Trip.class, trip1.getId());
            assertThat(actualResult.getStatus()).isEqualTo(trip1.getStatus());
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
            session.flush();
            session.clear();
            var trip1 = session.get(Trip.class, trip.getId());

            session.delete(trip1);
            session.flush();
            session.clear();

            var actualResult = session.get(Trip.class, trip1.getId());
            assertThat(actualResult).isNull();
        }
    }
}

