package ru.mcdev.dao.queryDslDao;

import org.hibernate.Session;
import ru.mcdev.dao.RepositoryBase;
import ru.mcdev.entity.Dispatcher;

public class DispatcherRepository extends RepositoryBase<Long, Dispatcher> {

    public DispatcherRepository(Session session) {
        super(Dispatcher.class, session);
    }
}
