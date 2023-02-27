package ru.mcdev.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.mcdev.entity.enums.Role;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(of = "email")
@DiscriminatorValue("dispatcher")
public class Dispatcher extends Employee {

    @OneToMany(mappedBy = "dispatcher", cascade = CascadeType.DETACH)
    private List<Trip> trips = new ArrayList<>();

    @Builder
    public Dispatcher(Long id, String email, String firstname, String lastname, String password, Role role, List<Trip> trips) {
        super(id, email, firstname, lastname, password, role);
        this.trips = trips;
    }

}
