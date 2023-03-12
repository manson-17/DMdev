package ru.mcdev.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ru.mcdev.entity.enums.Category;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


@NamedEntityGraph(
        name = "driverWithTrips",
        attributeNodes = {
                @NamedAttributeNode("trips")
        }
)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, exclude = "trips")
@EqualsAndHashCode(of = "email")
@Entity
@DiscriminatorValue("driver")
public class Driver extends Employee {

    @Enumerated(EnumType.STRING)
    private Category category;

    private String drivingRightsNumber;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.REMOVE)
    private List<Trip> trips = new ArrayList<>();

    public void addTrip(Trip trip) {
        trips.add(trip);
        trip.setDriver(this);
    }

}
