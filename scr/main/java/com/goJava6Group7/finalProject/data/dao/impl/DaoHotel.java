package com.goJava6Group7.finalProject.data.dao.impl;

import com.goJava6Group7.finalProject.data.dao.Dao;
import com.goJava6Group7.finalProject.data.dataBase.impl.DataBaseManagerFactory;
import com.goJava6Group7.finalProject.entities.Hotel;
import com.goJava6Group7.finalProject.entities.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        if (optional.isPresent()) {
            List<Long> idList = new ArrayList<>();
            optional.get().getRooms().forEach(i -> idList.add(i.getId()));
            if (hotels.remove(optional.get())){
                if (DataBaseManagerFactory.getDataBaseManager().getDaoRoom().deleteRoomsByHotelId(idList))
                    return DataBaseManagerFactory.getDataBaseManager().getDaoReservation().deleteReservationsByHotelId(idList);
            }
//            return true;
        }
        return false;
    }

    @Override
    public Hotel update(Hotel hotel) {
        Optional<Hotel> optional = hotels.stream().filter(i -> i.equals(hotel)).findFirst();
        if (optional.isPresent()) {
            hotels.remove(optional.get());
            hotels.add(hotel);
            return hotel;
        }
        return null;
    }

    @Override
    public Hotel get(Hotel hotel) {
        Optional<Hotel> optional = hotels.stream().filter(i -> i.equals(hotel)).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public List<Hotel> getAll() {
        return hotels;
    }

    public Hotel addRoomToHotel(Hotel hotel, Room room) {

        Optional<Hotel> optional = hotels.stream()
                .filter(i -> i.getId() == hotel.getId())
                .findFirst();

        if (optional.isPresent()) {
            Hotel existingHotel = optional.get();
            List<Room> rooms = existingHotel.getRooms();
            if (rooms == null) rooms = new ArrayList<>();
            rooms.add(room);
            existingHotel.setRooms(rooms);
            return existingHotel;
        }
        return null;
    }

    public Hotel updateRoomInHotel(Hotel hotel, Room room) {
//Проверки на наличие отеля, списка комнат отеля и самой комнаты нужны только,
// если будем использовать этот метод еще где-нибудь. На данный момент в этот метод
// передается уже существующий отель с существующей в нем комнатой
        Optional<Hotel> optionalHotel = hotels.stream()
                .filter(hotelInListOfHotels -> hotelInListOfHotels.getId() == hotel.getId())
                .findFirst();
        if (!optionalHotel.isPresent()) return null;

        List<Room> rooms = hotel.getRooms();
        if (rooms == null) return null;

        Optional<Room> optional = rooms.stream()
                .filter(roomInHotel -> roomInHotel.getId() == room.getId())
                .findFirst();
        if (optional.isPresent()) {
            rooms.remove(optional.get());
            rooms.add(room);
            hotel.setRooms(rooms);
            return hotel;
        }
        return null;
    }

    public boolean deleteRoom(Hotel hotel, Room room) {

        Optional<Hotel> optional = hotels.stream()
                .filter(i -> i.getId() == hotel.getId())
                .findFirst();
        if (optional.isPresent()) {
            Hotel existingHotel = optional.get();
            List<Room> rooms = existingHotel.getRooms();
//            if (rooms == null) return false;
//            return rooms.remove(room);
            return rooms != null && rooms.remove(room);
        }
        return false;
    }

    @Override
    public String toString() {
        return "DaoHotel{" +
                "hotels=" + hotels +
                '}';
    }
}
