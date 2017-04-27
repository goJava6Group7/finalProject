package scr.main.java.com.goJava6Group7.finalProject.data.dao.impl;

import scr.main.java.com.goJava6Group7.finalProject.data.dao.Dao;
import scr.main.java.com.goJava6Group7.finalProject.data.dataBase.impl.DataBaseManagerFactory;
import scr.main.java.com.goJava6Group7.finalProject.entities.Room;
import scr.main.java.com.goJava6Group7.finalProject.entities.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by Igor on 14.04.2017.
 */
public class DaoUser implements Dao<User> {
    private List<User> users;

    public DaoUser(List<User> users) {
        this.users = users;
    }

    @Override
    public User create(User user) {
       users.add(user);
       DataBaseManagerFactory.getDataBaseManager().updateDatabase();
       return user;
    }

    @Override
    public boolean delete(User user) {
        Optional<User> optional = users.stream().filter(i -> i.equals(user)).findFirst();
        if (optional.isPresent()){
            users.remove(optional.get());
            DataBaseManagerFactory.getDataBaseManager().updateDatabase();
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
            DataBaseManagerFactory.getDataBaseManager().updateDatabase();
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
