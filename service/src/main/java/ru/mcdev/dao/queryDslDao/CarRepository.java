package ru.mcdev.dao.queryDslDao;

import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Session;
import ru.mcdev.dao.RepositoryBase;
import ru.mcdev.dao.predicates.QPredicate;
import ru.mcdev.dto.CarFilter;
import ru.mcdev.entity.Car;

import java.util.List;
import java.util.Optional;

import static ru.mcdev.entity.QCar.car;
import static ru.mcdev.entity.QTrip.trip;

public class CarRepository extends RepositoryBase<Long, Car> {

    public CarRepository(Session session) {
        super(Car.class, session);
    }

    public Optional<Car> findByTrip(Long tripId) {
        return Optional.ofNullable(new JPAQuery<Car>(getSession())
                .select(car)
                .from(car)
                .innerJoin(car.trips, trip)
                .where(trip.id.eq(tripId))
                .fetchOne());
    }

    public List<Car> findBy(CarFilter filter) {

        var predicate = QPredicate.builder()
                .add(filter.getBrand(), car.brand::eq)
                .add(filter.getModel(), car.model::eq)
                .add(filter.getCategory(), car.category::eq)
                .add(filter.getRegistrationNumber(), car.registrationNumber::eq)
                .buildAnd();

        return new JPAQuery<Car>(getSession())
                .select(car)
                .from(car)
                .where(predicate)
                .fetch();
    }
}
