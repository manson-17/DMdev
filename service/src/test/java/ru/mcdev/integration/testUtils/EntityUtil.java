package ru.mcdev.integration.testUtils;

import lombok.experimental.UtilityClass;
import ru.mcdev.entity.Car;
import ru.mcdev.entity.Dispatcher;
import ru.mcdev.entity.Driver;
import ru.mcdev.entity.enums.Category;
import ru.mcdev.entity.enums.Role;

import static java.util.Collections.emptyList;

@UtilityClass
public class EntityUtil {

    public Driver createDriver() {
        return Driver.builder()
                .email("testDriver@mail.com")
                .firstname("Petya")
                .lastname("Zubkin")
                .role(Role.DRIVER)
                .password("qwerty")
                .category(Category.C)
                .drivingRightsNumber("0123")
                .trips(emptyList())
                .build();
    }

    public Dispatcher createDispatcher() {
        return Dispatcher.builder()
                .email("testDispatcher@mail.com")
                .firstname("Vasya")
                .lastname("Pupkin")
                .role(Role.DISPATCHER)
                .password("qwerty")
                .trips(emptyList())
                .build();
    }

    public Car createCar() {
        return Car.builder()
                .model("model")
                .brand("brand")
                .category(Category.B)
                .registrationNumber("1")
                .trips(emptyList())
                .build();
    }

}
