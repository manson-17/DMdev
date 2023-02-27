package ru.mcdev.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.mcdev.entity.enums.Status;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id", "dispatcher", "driver", "car"})
public class Trip extends AuditableEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String placeOfDeparture;

    private String destination;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "dispatcher_id", referencedColumnName = "id")
    private Dispatcher dispatcher;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private Driver driver;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
        this.dispatcher.getTrips().add(this);
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
        this.driver.getTrips().add(this);
    }

    public void setCar(Car car) {
        this.car = car;
        this.car.getTrips().add(this);
    }



}
