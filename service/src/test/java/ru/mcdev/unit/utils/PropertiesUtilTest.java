package ru.mcdev.unit.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.mcdev.utils.PropertiesUtil;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PropertiesUtilTest {

    @ParameterizedTest
    @MethodSource("getPropertyArguments")
    void checkGetTest(String key, String expectedValue) {

        var actual = PropertiesUtil.get(key);

        assertThat(actual).isEqualTo(expectedValue);
    }

    static Stream<Arguments> getPropertyArguments() {
        return Stream.of(
                Arguments.of("hibernate.connection.driver_class", "org.postgresql.Driver"),
                Arguments.of("hibernate.hbm2ddl.auto", "create"),
                Arguments.of("hibernate.format_sql", "true"),
                Arguments.of("hibernate.show_sql", "true"),
                Arguments.of("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect")
        );
    }

}