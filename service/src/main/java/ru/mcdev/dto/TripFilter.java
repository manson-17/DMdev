package ru.mcdev.dto;

import lombok.Builder;
import lombok.Value;
import ru.mcdev.entity.Car;
import ru.mcdev.entity.Dispatcher;
import ru.mcdev.entity.Driver;
import ru.mcdev.entity.enums.Status;

import java.math.BigDecimal;

@Value
@Builder
public class TripFilter {

    Status status;
    String placeOfDeparture;
    String destination;
    BigDecimal cost;
    Dispatcher dispatcher;
    Driver driver;
    Car car;
}
