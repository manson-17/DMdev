package ru.mcdev.integration.dao;

import org.hibernate.graph.GraphSemantic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.mcdev.dao.queryDslDao.CarRepository;
import ru.mcdev.dto.CarFilter;
import ru.mcdev.entity.Car;
import ru.mcdev.entity.enums.Category;
import ru.mcdev.integration.testUtils.EntityUtil;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CarRepositoryIT extends BaseIntegrationTest {

    private CarRepository carRepository;

    @BeforeEach
    void init() {
        carRepository = new CarRepository(session);
    }

    @Test
    void findById() {

        Map<String, Object> properties = Map.of(
                GraphSemantic.FETCH.getJpaHintName(), carRepository.getSession().getEntityGraph("carWithTrips")
        );

        Optional<Car> actualResult = carRepository.findById(1L, properties);

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get().getRegistrationNumber()).isEqualTo("000");
    }

    @Test
    void findAll() {

        var actualResult = carRepository.findAll();

        assertThat(actualResult.stream().map(Car::getRegistrationNumber).toList())
                .containsExactlyInAnyOrder("000", "001", "100", "111");
    }

    @Test
    void save() {

        var car = EntityUtil.createCar();
        carRepository.save(car);
        carRepository.getSession().clear();
        carRepository.getSession().flush();

        var actualResult = carRepository.findById(car.getId());

        assertThat(actualResult).isPresent();
    }

    @Test
    void delete() {

        var car = carRepository.findById(1L).get();
        carRepository.delete(car.getId());

        var actualResult = carRepository.findById(car.getId());

        assertThat(actualResult).isEmpty();
    }

    @Test
    void update() {

        var car = carRepository.findById(1L).get();
        car.setRegistrationNumber("new");
        carRepository.update(car);
        carRepository.getSession().flush();
        carRepository.getSession().clear();

        var actualResult = carRepository.findById(car.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get().getRegistrationNumber()).isEqualTo("new");
    }

    @Test
    void findByTrip() {

        Optional<Car> actualResult = carRepository.findByTrip(5L);

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get().getBrand()).isEqualTo("Trabant");
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForFindBy")
    void findBy(CarFilter filter, List<Car> expectedResult) {

        List<Car> actualResult = carRepository.findBy(filter);

        assertThat(actualResult).hasSize(expectedResult.size()).containsAll(expectedResult);
    }

    static Stream<Arguments> getArgumentsForFindBy() {
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