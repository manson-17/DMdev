package ru.mcdev.dto;

import lombok.Builder;
import lombok.Value;
import ru.mcdev.entity.enums.Role;

@Value
@Builder
public class DispatcherFilter {
    String email;
    String firstname;
    String lastname;
    String password;
    Role role;
}
