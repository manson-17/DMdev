package ru.mcdev.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.mcdev.entity.enums.Category;
import ru.mcdev.entity.enums.Role;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(of = "email")
@DiscriminatorValue("driver")
public class Driver extends Employee {

    @Enumerated(EnumType.STRING)
    private Category category;
    private String drivingRightsNumber;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.DETACH)
    private List<Trip> trips = new ArrayList<>();

    @Builder
    public Driver(Long id, String email, String firstname, String lastname, String password, Role role, Category category, String drivingRightsNumber, List<Trip> trips) {
        super(id, email, firstname, lastname, password, role);
        this.category = category;
        this.drivingRightsNumber = drivingRightsNumber;
        this.trips = trips;
    }

}
