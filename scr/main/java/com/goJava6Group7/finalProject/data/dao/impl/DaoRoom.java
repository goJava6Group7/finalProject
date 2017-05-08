package com.goJava6Group7.finalProject.data.dao.impl;

import com.goJava6Group7.finalProject.data.dao.Dao;
import com.goJava6Group7.finalProject.entities.Room;

import java.util.List;
import java.util.Optional;

/**
 * Created by Igor on 14.04.2017.
 */
public class DaoRoom implements Dao<Room> {
    private List<Room> rooms;

    public DaoRoom(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public Room create(Room room) {
        this.rooms.add(room);
//        DataBaseManagerFactory.getDataBaseManager().updateDatabase();
        return room;
    }

    @Override
    public boolean delete(Room room){
        Optional<Room> optional = rooms.stream().filter(i -> i.equals(room)).findFirst();
        if (optional.isPresent()){
            rooms.remove(optional.get());
            return true;
        }
         return false;
    }

    @Override
    public Room update(Room room) {
        Optional<Room> optional = rooms.stream().filter(i -> i.equals(room)).findFirst();
        if (optional.isPresent()){
            rooms.remove(optional.get());
            rooms.add(room);
            return room;
        }
        return null;
    }

    @Override
    public Room get(Room room) {
        Optional<Room> optional = rooms.stream().filter(i -> i.equals(room)).findFirst();
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public List<Room> getAll() {
        return rooms;
    }

    @Override
    public String toString() {
        return "DaoRoom{" +
                "rooms=" + rooms +
                '}';
    }
}
