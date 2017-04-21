package com.goJava6Group7.finalProject.entities;

import java.util.List;

/**
 * Created by Igor on 13.04.2017.
 */
public class Hotel extends Entity {

    //TODO Kontar Maryna changes these for  ProjectController   findHotelByHotelName
    private String hotelName;
    private List<Room> rooms;
    private String hotelCity;

    public String getHotelName() {
        return hotelName;
    }

    public String getHotelCity() {return hotelCity;}

    public List<Room> getHotelRooms() {return rooms;}

}
