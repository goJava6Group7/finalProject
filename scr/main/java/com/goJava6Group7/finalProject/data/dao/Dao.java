package scr.main.java.com.goJava6Group7.finalProject.data.dao;

import java.util.List;

/**
 * Created by Igor on 13.04.2017.
 */
public interface Dao<T> {

    T create(T entity);

    boolean delete(T entity);

    T update(T entity);

    T get(T entity);

    List<T> getAll();
}
