package ru.mcdev.integration.dao.criteria;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mcdev.dao.criteriaDao.DriverRepositoryCriteria;
import ru.mcdev.entity.Driver;
import ru.mcdev.integration.dao.BaseIntegrationTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DriverRepositoryCriteriaIT extends BaseIntegrationTest {

    private DriverRepositoryCriteria driverRepositoryCriteria;

    @BeforeEach
    void init() {
        driverRepositoryCriteria = new DriverRepositoryCriteria(session);
    }

    @Test
    void findDriversWithMaxTrips() {

        List<Driver> driverWithMaxTrips = driverRepositoryCriteria.findDriversSortedByTripsDesc(1);

        assertThat(driverWithMaxTrips.get(0).getEmail()).isEqualTo("email1@mail.com");
    }
}