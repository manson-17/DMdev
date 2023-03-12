package ru.mcdev.integration.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mcdev.dao.queryDslDao.DriverRepository;
import ru.mcdev.dto.DriverFilter;
import ru.mcdev.entity.Driver;
import ru.mcdev.integration.testUtils.EntityUtil;

import static org.assertj.core.api.Assertions.assertThat;

class DriverRepositoryIT extends BaseIntegrationTest {

    private DriverRepository driverRepository;

    @BeforeEach
    void init() {
        driverRepository = new DriverRepository(session);
    }

    @Test
    void findById() {

        var actualResult = driverRepository.findById(5L);

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get().getEmail()).isEqualTo("email5@mail.com");
    }

    @Test
    void save() {

        var driver = EntityUtil.createDriver();
        driverRepository.save(driver);
        driverRepository.getSession().flush();
        driverRepository.getSession().clear();

        var actualResult = driverRepository.findById(driver.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(driver);
    }

    @Test
    void update() {

        var driver = driverRepository.findById(5L).get();
        driver.setDrivingRightsNumber("new");
        driverRepository.update(driver);
        driverRepository.getSession().flush();
        driverRepository.getSession().clear();

        var actualResult = driverRepository.findById(5L);

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get().getDrivingRightsNumber()).isEqualTo("new");
    }

    @Test
    void delete() {

        var driver = driverRepository.findById(5L);
        driverRepository.delete(driver.get().getId());

        var actualResult = driverRepository.findById(5L);

        assertThat(actualResult).isEmpty();
    }

    @Test
    void findAll() {

        var actualResult = driverRepository.findAll();

        assertThat(actualResult.stream().map(Driver::getEmail).toList())
                .containsExactlyInAnyOrder("email1@mail.com", "email2@mail.com", "email3@mail.com",
                        "email4@mail.com", "email5@mail.com");
    }

    @Test
    void findDriversWithMaxTrips() {

        var driverWithMaxTrips = driverRepository.findDriversSortedByTripsDesc(2);

        assertThat(driverWithMaxTrips.get(0).getEmail()).isEqualTo("email1@mail.com");
    }

    @Test
    void findByEmail() {

        var filter = DriverFilter.builder().email("email1@mail.com").build();

        var actualResult = driverRepository.findBy(filter);

        assertThat(actualResult.get(0).getDrivingRightsNumber()).isEqualTo("000001");
    }

}













