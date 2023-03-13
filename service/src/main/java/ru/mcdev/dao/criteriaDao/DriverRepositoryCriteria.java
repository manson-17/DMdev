package ru.mcdev.dao.criteriaDao;

import org.hibernate.Session;
import ru.mcdev.dao.RepositoryBase;
import ru.mcdev.entity.Driver;
import ru.mcdev.entity.Driver_;
import ru.mcdev.entity.Trip_;

import java.util.List;

public class DriverRepositoryCriteria extends RepositoryBase<Long, Driver> {

    public DriverRepositoryCriteria(Session session) {
        super(Driver.class, session);
    }

    public List<Driver> findDriversSortedByTripsDesc(Integer limit) {
        var session = getSession();
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Driver.class);
        var driver = criteria.from(Driver.class);
        var trips = driver.join(Driver_.trips);

        criteria.select(driver)
                .groupBy(driver.get(Driver_.id))
                .orderBy(cb.desc(cb.count(trips.get(Trip_.driver).get(Driver_.id))));
        return session.createQuery(criteria).setMaxResults(limit).list();
    }

}
