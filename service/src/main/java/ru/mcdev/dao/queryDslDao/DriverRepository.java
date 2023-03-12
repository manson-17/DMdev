package ru.mcdev.dao.queryDslDao;

import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Session;
import ru.mcdev.dao.RepositoryBase;
import ru.mcdev.dao.predicates.QPredicate;
import ru.mcdev.dto.DriverFilter;
import ru.mcdev.entity.Driver;

import java.util.List;

import static ru.mcdev.entity.QDriver.driver;
import static ru.mcdev.entity.QTrip.trip;

public class DriverRepository extends RepositoryBase<Long, Driver> {

    public DriverRepository(Session session) {
        super(Driver.class, session);
    }

    public List<Driver> findDriversSortedByTripsDesc(Integer limit) {
        return new JPAQuery<Driver>(getSession())
                .select(driver)
                .from(driver)
                .join(driver.trips, trip)
                .groupBy(driver.id)
                .orderBy(trip.driver.id.count().desc())
                .limit(limit)
                .fetch();
    }

    public List<Driver> findBy(DriverFilter filter) {

        var predicate = QPredicate.builder()
                .add(filter.getRole(), driver.role::eq)
                .add(filter.getLastname(), driver.lastname::eq)
                .add(filter.getCategory(), driver.category::eq)
                .add(filter.getEmail(), driver.email::eq)
                .add(filter.getFirstname(), driver.firstname::eq)
                .add(filter.getDrivingRightsNumber(), driver.drivingRightsNumber::eq)
                .buildAnd();

        return new JPAQuery<Driver>(getSession())
                .select(driver)
                .from(driver)
                .where(predicate)
                .fetch();
    }
}
