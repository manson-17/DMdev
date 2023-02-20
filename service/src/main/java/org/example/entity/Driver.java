package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(schema = "atp", name = "driver")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Driver {

    @Id
    private Long id;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

//    @OneToOne(fetch = FetchType.LAZY)
//    @MapsId
    private Employee employee;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "car_id", referencedColumnName = "id")
//    private Car car;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return id.equals(driver.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return firstname + lastname;
    }

}
