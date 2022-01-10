package com.eltonb.datatier.jdbc.dao.interfaces;

import java.util.List;

public interface BaseDao <T, K> {
    T find(K key);
    List<T> findAll();
    void insert(T t);
    void update(T t);
    void delete(T t);
}
