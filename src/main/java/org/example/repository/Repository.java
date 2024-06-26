package org.example.repository;

import java.util.List;

public interface Repository<T, K> {
    T get(K id);

    List<T> getAll();

    T save(T t);

    void update(T t);

    void delete(K id);
}
