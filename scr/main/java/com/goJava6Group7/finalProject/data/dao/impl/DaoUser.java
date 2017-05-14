package com.goJava6Group7.finalProject.data.dao.impl;

import com.goJava6Group7.finalProject.data.dao.Dao;
import com.goJava6Group7.finalProject.entities.User;

import java.util.List;
import java.util.Optional;

public class DaoUser implements Dao<User> {
    private List<User> users;

    public DaoUser(List<User> users) {
        this.users = users;
    }

    @Override
    public User create(User user) {
       users.add(user);
       return user;
    }

    @Override
    public boolean delete(User user) {
        Optional<User> optional = users.stream().filter(i -> i.equals(user)).findFirst();
        if (optional.isPresent()){
            users.remove(optional.get());
            return true;
        }
        return false;
    }

    @Override
    public User update(User user) {
        Optional<User> optional = users.stream().filter(i -> i.equals(user)).findFirst();
        if (optional.isPresent()){
            users.remove(optional.get());
            users.add(user);
            return user;
        }
        return null;
    }

    @Override
    public User get(User user) {
        Optional<User> optional = users.stream().filter(i -> i.equals(user)).findFirst();
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public String toString() {
        return "DaoUser{" +
                "users=" + users +
                '}';
    }
}
