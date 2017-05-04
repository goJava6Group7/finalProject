package com.goJava6Group7.finalProject.controllers;

import com.goJava6Group7.finalProject.data.dataBase.DataBaseManager;
import com.goJava6Group7.finalProject.entities.Hotel;
import com.goJava6Group7.finalProject.entities.Reservation;
import com.goJava6Group7.finalProject.entities.Room;
import com.goJava6Group7.finalProject.entities.User;

/**
 * Created by Igor on 13.04.2017.
 */
public class ProjectController {

    public ProjectController(DataBaseManager dbManager) {
    }

    public User createAccount() {
        throw new UnsupportedOperationException();
    }

    public Reservation reserveRoom() {
        throw new UnsupportedOperationException();
    }

    public boolean cancelRoomReservation() {
        throw new UnsupportedOperationException();
    }

    public Hotel findHotelByHotelName(String hotelName) {
        throw new UnsupportedOperationException();
    }

    public Hotel findHotelByCityName(String cityName) {
        throw new UnsupportedOperationException();
    }

    public Room findRoomInHotel(Room room, Hotel hotel) {
        throw new UnsupportedOperationException();
    }


}
