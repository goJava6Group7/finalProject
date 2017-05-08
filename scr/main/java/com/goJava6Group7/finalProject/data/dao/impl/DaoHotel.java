package com.goJava6Group7.finalProject.data.dao.impl;

import com.goJava6Group7.finalProject.data.dao.Dao;
import com.goJava6Group7.finalProject.data.dataBase.DataBaseManager;
import com.goJava6Group7.finalProject.data.dataBase.impl.DataBaseManagerFactory;
import com.goJava6Group7.finalProject.entities.Hotel;
import com.goJava6Group7.finalProject.entities.Room;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

/**
 * Created by Igor on 14.04.2017.
 */
public class DaoHotel implements Dao<Hotel> {
    private List<Hotel> hotels;

    public DaoHotel(List<Hotel> hotels) {
        this.hotels = hotels;
    }

    @Override
    public Hotel create(Hotel hotel) {
        hotels.add(hotel);
        return hotel;
    }

    @Override
    public boolean delete(Hotel hotel) {
        Optional<Hotel> optional = hotels.stream().filter(i -> i.equals(hotel)).findFirst();
        if (optional.isPresent()){
            hotels.remove(optional.get());
            return true;
        }
        return false;
    }

    @Override
    public Hotel update(Hotel hotel) {
        Optional<Hotel> optional = hotels.stream().filter(i -> i.equals(hotel)).findFirst();
        if (optional.isPresent()){
            hotels.remove(optional.get());
            hotels.add(hotel);
            return hotel;
        }
        return null;
    }

    @Override
    public Hotel get(Hotel hotel) {
        Optional<Hotel> optional = hotels.stream().filter(i -> i.equals(hotel)).findFirst();
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public List<Hotel> getAll() {
        return hotels;
    }

    @Override
    public String toString() {
        return "DaoHotel{" +
                "hotels=" + hotels +
                '}';
    }
}
