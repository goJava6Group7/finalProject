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

        Optional<Hotel> optional = hotels.stream()
                .filter(i -> i.equals(hotel)).findFirst();
        if (!optional.isPresent()) return false;

        List<Long> idList = new ArrayList<>();

        List<Room> rooms = optional.get().getRooms();//все комнаты отеля
        if (rooms != null) {

            rooms.forEach(room -> idList.add(room.getId()));
//оставить именно такую последовательность delete, иначе не сможет искать room.getId() (reservation.getRoomID())
            if (DataBaseManagerFactory.getDataBaseManager().getDaoRoom()
                    .deleteRoomsByHotelId(idList))
                DataBaseManagerFactory.getDataBaseManager()
                        .getDaoReservation().deleteReservationsByHotelId(idList);
        }
        return hotels.remove(optional.get());
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
                .filter(hotelInListOfHotels -> hotelInListOfHotels.equals(hotel)) //hotelInListOfHotels.getId() == hotel.getId())
                .findFirst();
        if (!optionalHotel.isPresent()) return null;

        List<Room> rooms = optionalHotel.get().getRooms();
        if (rooms.isEmpty()) return null;

        Optional<Room> optionalRoom = rooms.stream()
                .filter(roomInHotel -> roomInHotel.equals(room)) //roomInHotel.getId() == room.getId())
                .findFirst();
        if (optionalRoom.isPresent()) {
            rooms.remove(optionalRoom.get());
            rooms.add(room);
//            hotel.setRooms(rooms);
            return hotel;
        }
        return null;
    }

    public boolean deleteRoomFromHotel(Hotel hotel, Room room) {

        Optional<Hotel> optional = hotels.stream()
                .filter(i -> i.getId() == hotel.getId())
                .findFirst();
        if (optional.isPresent()) {
            Hotel existingHotel = optional.get();
            List<Room> rooms = existingHotel.getRooms();
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
