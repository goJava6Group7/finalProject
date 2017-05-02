package com.goJava6Group7.finalProject.entities;

import java.util.List;

import static com.goJava6Group7.finalProject.utils.IdUtil.IdGenerator;

/**
 * Created by Igor on 13.04.2017.
 */
public class Hotel extends Entity {

    //TODO Kontar Maryna changes these for  ProjectController   findHotelByHotelName() and Menu mainMenu() case 2
    private String id;
    private String hotelName;
    private List<Room> rooms;
    private String hotelCity;

    public Hotel(String hotelName, String hotelCity) {
        id = IdGenerator();
        this.hotelName = hotelName;
        this.hotelCity = hotelCity;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getHotelCity() {return hotelCity;}

    public List<Room> getHotelRooms() {return rooms;}

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hotel hotel = (Hotel) o;

        return id != null ? id.equals(hotel.id) : hotel.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    //TODO Kontar Maryna changes these for Menu mainMenu() case 2
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Hotel{");
        sb.append("hotelName='").append(hotelName).append('\'');
        sb.append("; rooms=").append(rooms.stream()
                .map(room -> " Room name: " + room.getName()
                        + ", Number of persons: " + room.getNumberOfPersons()
                        + ", Price: " + room.getPrice()).reduce("", String::concat));
        sb.append("; hotelCity='").append(hotelCity).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
