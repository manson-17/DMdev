package ru.mcdev.dto;

import lombok.Builder;
import lombok.Value;
import ru.mcdev.entity.enums.Category;

@Value
@Builder
public class CarFilter {
    String brand;
    String model;
    Category category;
    String registrationNumber;
}
