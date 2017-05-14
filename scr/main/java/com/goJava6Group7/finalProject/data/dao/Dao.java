package com.goJava6Group7.finalProject.data.dao;

import java.util.List;


public interface Dao<T> {

    T create(T entity);

    boolean delete(T entity);

    T update(T entity);

    T get(T entity);

    List<T> getAll();
}
