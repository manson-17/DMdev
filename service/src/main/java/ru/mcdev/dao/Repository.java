package ru.mcdev.dao;


import ru.mcdev.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyMap;

public interface Repository<K extends Serializable, T extends BaseEntity<K>> {

    void delete(K id);

    void update(T entity);

    T save(T entity);

    Optional<T> findById(K id, Map<String, Object> properties);

    default Optional<T> findById(K id) {
        return findById(id, emptyMap());
    }

    List<T> findAll();
}
