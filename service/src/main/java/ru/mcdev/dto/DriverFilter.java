package ru.mcdev.dto;

import lombok.Builder;
import lombok.Value;
import ru.mcdev.entity.enums.Category;
import ru.mcdev.entity.enums.Role;

@Value
@Builder
public class DriverFilter {

    String email;
    String firstname;
    String lastname;
    Role role;
    Category category;
    String drivingRightsNumber;
}
