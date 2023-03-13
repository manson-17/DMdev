package ru.mcdev.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.mcdev.entity.enums.Status;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;


@Data
@Entity
@Builder
@NoArgsConstructor
@ToString(exclude = {"id", "dispatcher", "driver", "car"})
@EqualsAndHashCode(exclude = {"id", "dispatcher", "driver", "car"})
public class Trip extends AuditableEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String placeOfDeparture;

    private String destination;

    private BigDecimal cost;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Dispatcher dispatcher;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Driver driver;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Car car;

    public void addDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
        this.dispatcher.getTrips().add(this);
    }

    public void addDriver(Driver driver) {
        this.driver = driver;
        this.driver.getTrips().add(this);
    }

    public void addCar(Car car) {
        this.car = car;
        this.car.getTrips().add(this);
    }

    public Trip(Long id, Status status, String placeOfDeparture, String destination, BigDecimal cost, Dispatcher dispatcher, Driver driver, Car car) {
        this.id = id;
        this.status = status;
        this.placeOfDeparture = placeOfDeparture;
        this.destination = destination;
        this.cost = cost;
        this.dispatcher = dispatcher;
        this.driver = driver;
        this.car = car;
    }
}
