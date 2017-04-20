package com.goJava6Group7.finalProject.entities;

import java.util.List;

/**
 * Created by Igor on 13.04.2017.
 */
public class Hotel extends Entity {

    //TODO Kontar Maryna changes these for  ProjectController   findHotelByHotelName
    private String hotelName;
    private List<Room> rooms;

    public String getHotelName() {
        return hotelName;
    }
}
