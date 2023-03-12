package ru.mcdev.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import ru.mcdev.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class RepositoryBase<K extends Serializable, T extends BaseEntity<K>> implements Repository<K, T> {

    private final Class<T> clazz;
    @Getter
    private final Session session;

    @Override
    public T save(T entity) {
        session.persist(entity);
        return entity;
    }

    @Override
    public void delete(K id) {
        session.remove(session.find(clazz, id));
        session.flush();
    }

    @Override
    public void update(T entity) {
        session.merge(entity);
    }

    @Override
    public Optional<T> findById(K id, Map<String, Object> properties) {
        return Optional.ofNullable(session.find(clazz, id, properties));
    }

    @Override
    public List<T> findAll() {
        var criteria = session.getCriteriaBuilder().createQuery(clazz);
        criteria.from(clazz);
        return session.createQuery(criteria).getResultList();
    }
}
