package ru.mcdev.integration.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mcdev.dao.queryDslDao.CarRepository;
import ru.mcdev.dao.queryDslDao.DispatcherRepository;
import ru.mcdev.dao.queryDslDao.DriverRepository;
import ru.mcdev.dao.queryDslDao.TripRepository;
import ru.mcdev.entity.Trip;
import ru.mcdev.entity.enums.Status;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class TripRepositoryIT extends BaseIntegrationTest {

    private TripRepository tripRepository;
    private DriverRepository driverRepository;
    private DispatcherRepository dispatcherRepository;
    private CarRepository carRepository;

    @BeforeEach
    void init() {
        tripRepository = new TripRepository(session);
        dispatcherRepository = new DispatcherRepository(session);
        driverRepository = new DriverRepository(session);
        carRepository = new CarRepository(session);
    }

    @Test
    void findById() {

        var actualResult = tripRepository.findById(2L);

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get().getCost()).isEqualTo(BigDecimal.valueOf(150.01));
    }

    @Test
    void save() {

        var dispatcher = dispatcherRepository.findById(7L).get();
        var car = carRepository.findById(1L).get();
        var driver = driverRepository.findById(5L).get();
        dispatcherRepository.save(dispatcher);
        carRepository.save(car);
        driverRepository.save(driver);
        tripRepository.getSession().flush();
        tripRepository.getSession().clear();
        var trip = Trip.builder()
                .cost(BigDecimal.valueOf(10, 2))
                .car(car)
                .driver(driver)
                .dispatcher(dispatcher)
                .status(Status.READY)
                .build();
        tripRepository.save(trip);
        tripRepository.getSession().flush();
        tripRepository.getSession().clear();

        var actualResult = tripRepository.findById(trip.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(trip);
    }

    @Test
    void update() {

        var trip = tripRepository.findById(2L).get();
        trip.setCost(BigDecimal.valueOf(10.00));
        tripRepository.update(trip);
        tripRepository.getSession().flush();
        tripRepository.getSession().clear();

        var actualResult = tripRepository.findById(2L);

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get().getCost()).isEqualByComparingTo(BigDecimal.valueOf(10.00));
    }

    @Test
    void delete() {

        var trip = tripRepository.findById(7L);
        tripRepository.delete(trip.get().getId());

        var actualResult = tripRepository.findById(7L);

        assertThat(actualResult).isEmpty();
    }

    @Test
    void findAll() {

        var actualResult = tripRepository.findAll();

        assertThat(actualResult.stream().map(Trip::getCost).toList())
                .containsExactlyInAnyOrder(
                        BigDecimal.valueOf(50.00).setScale(2),
                        BigDecimal.valueOf(150.01),
                        BigDecimal.valueOf(75.00).setScale(2),
                        BigDecimal.valueOf(76.00).setScale(2),
                        BigDecimal.valueOf(45.00).setScale(2),
                        BigDecimal.valueOf(55.00).setScale(2),
                        BigDecimal.valueOf(50.00).setScale(2),
                        BigDecimal.valueOf(36.00).setScale(2),
                        BigDecimal.valueOf(89.00).setScale(2),
                        BigDecimal.valueOf(56.00).setScale(2),
                        BigDecimal.valueOf(68.00).setScale(2),
                        BigDecimal.valueOf(38.00).setScale(2),
                        BigDecimal.valueOf(56.00).setScale(2),
                        BigDecimal.valueOf(51.00).setScale(2),
                        BigDecimal.valueOf(74.00).setScale(2)
                );
    }

}