package com.teamtreehouse.instateam.dao;

import java.util.List;

public interface GenericDao<T> {
    T findById(Long id);
    List<T> findAll();
    void save(T object);
    void delete(T delete);
}
