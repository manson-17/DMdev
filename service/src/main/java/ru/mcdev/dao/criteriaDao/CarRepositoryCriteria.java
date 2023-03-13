package ru.mcdev.dao.criteriaDao;

import org.hibernate.Session;
import ru.mcdev.dao.RepositoryBase;
import ru.mcdev.dao.predicates.CriteriaPredicate;
import ru.mcdev.dto.CarFilter;
import ru.mcdev.entity.Car;
import ru.mcdev.entity.Car_;
import ru.mcdev.entity.Trip_;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;


public class CarRepositoryCriteria extends RepositoryBase<Long, Car> {

    public CarRepositoryCriteria(Session session) {
        super(Car.class, session);
    }

    public List<Car> findAllBy(CarFilter filter) {

        var cb = getSession().getCriteriaBuilder();
        var criteria = cb.createQuery(Car.class);
        var car = criteria.from(Car.class);

        var predicates = CriteriaPredicate.builder()
                .add(filter.getCategory(), it -> cb.equal(car.get(Car_.category), it))
                .add(filter.getModel(), it -> cb.equal(car.get(Car_.model), it))
                .add(filter.getBrand(), it -> cb.equal(car.get(Car_.brand), it))
                .add(filter.getRegistrationNumber(), it -> cb.equal(car.get(Car_.registrationNumber), it))
                .build();

        criteria.select(car).where(predicates.toArray(Predicate[]::new));
        return getSession().createQuery(criteria).list();

    }

    public Optional<Car> findByTrip(Long tripId) {
        var cb = getSession().getCriteriaBuilder();
        var criteria = cb.createQuery(Car.class);
        var car = criteria.from(Car.class);
        var trips = car.join(Car_.trips);

        criteria.select(car).where(
                cb.equal(trips.get(Trip_.id), tripId)
        );
        return getSession().createQuery(criteria).uniqueResultOptional();

    }

}
