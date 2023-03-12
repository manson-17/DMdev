package ru.mcdev.dao.queryDslDao;

import org.hibernate.Session;
import ru.mcdev.dao.RepositoryBase;
import ru.mcdev.entity.Trip;

public class TripRepository extends RepositoryBase<Long, Trip> {

    public TripRepository(Session session) {
        super(Trip.class, session);
    }
}
