package ru.mcdev.integration.dao.criteria;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.mcdev.dao.criteriaDao.CarRepositoryCriteria;
import ru.mcdev.dto.CarFilter;
import ru.mcdev.entity.Car;
import ru.mcdev.entity.enums.Category;
import ru.mcdev.integration.dao.BaseIntegrationTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class CarRepositoryCriteriaIT extends BaseIntegrationTest {

    private CarRepositoryCriteria carCriteriaDao;

    @BeforeEach
    void init() {
        carCriteriaDao = new CarRepositoryCriteria(session);
    }

    @Test
    void findAll() {

        List<Car> all = carCriteriaDao.findAll();
        List<String> actualResult = all.stream().map(Car::getRegistrationNumber).toList();

        assertThat(actualResult).containsExactlyInAnyOrder("000", "001", "100", "111");
    }

    @Test
    void findByTrip() {

        Optional<Car> actualResult = carCriteriaDao.findByTrip(5L);

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get().getBrand()).isEqualTo("Trabant");
    }

    @ParameterizedTest
    @MethodSource("getArguments")
    void findAllByBrand(CarFilter filter, List<Car> expectedResult) {

        List<Car> actualResult = carCriteriaDao.findAllBy(filter);

        assertThat(actualResult).hasSize(expectedResult.size()).containsAll(expectedResult);
    }

    static Stream<Arguments> getArguments() {
        return Stream.of(
                Arguments.of(CarFilter.builder().category(Category.C).build(),
                        List.of(
                                Car.builder().model("gto").category(Category.C).brand("Pontiac").registrationNumber("111").build(),
                                Car.builder().brand("Trabant").model("p601").category(Category.C).registrationNumber("001")
                                        .build()
                        )
                ),
                Arguments.of(CarFilter.builder().model("fiero").build(),
                        List.of(
                                Car.builder().model("fiero").category(Category.B).brand("Pontiac").registrationNumber("000")
                                        .build(),
                                Car.builder().brand("Pontiac").model("fiero").category(Category.B).registrationNumber("100")
                                        .build()
                        )
                ),
                Arguments.of(CarFilter.builder().registrationNumber("000").build(),
                        List.of(
                                Car.builder().model("fiero").category(Category.B).brand("Pontiac").registrationNumber("000")
                                        .build()
                        )
                ),
                Arguments.of(CarFilter.builder().category(Category.C).brand("Trabant").build(),
                        List.of(
                                Car.builder().brand("Trabant").model("p601").category(Category.C).registrationNumber("001")
                                        .build()
                        )
                )
        );
    }
}
