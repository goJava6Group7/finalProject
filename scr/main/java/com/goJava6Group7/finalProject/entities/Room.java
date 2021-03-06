package com.goJava6Group7.finalProject.entities;

import com.goJava6Group7.finalProject.utils.IdUtil;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Room extends Entity {
    @XmlElement
    private long id;
    @XmlElement
    private int capacity;
    @XmlElement
    private int price;
    @XmlElement
    private RoomClass roomClass;
    @XmlElement
    private long hotelID;
    @XmlElement
    private List<Reservation> bookings = new ArrayList<>();

    public enum  RoomParameters{CAPACITY, ROOM_CLASS, PRICE}

    public Room() {
    }



    public Room(int capacity, int price, RoomClass roomClass, long hotelID) {
        this.id = IdUtil.getRoomId();
        this.capacity = capacity;
        this.price = price;
        this.roomClass = roomClass;
        this.hotelID = hotelID;
    }

    @Override
    public String getOutput(){

        String output = String.format("%-4d \t %-6d \t %-4d \t %-20s", this.getId(), this.getCapacity(), this.getPrice(), this.getRoomClass());

        return output;

    }

//    @Override
    public static String getOutputHeader() {

        String header = String.format("%-4s \t %-4s \t %-4s \t %-4s \t", "id", "capacity", "price", "room class");

        return header;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        return room.id == this.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public long getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public RoomClass getRoomClass() {
        return roomClass;
    }

    public void setRoomClass(RoomClass roomClass) {
        this.roomClass = roomClass;
    }

    public List<Reservation> getBookings() {
        return bookings;
    }

    public void setBookings(List<Reservation> bookings) {
        this.bookings = bookings;
    }

    public long getHotelID() {
        return hotelID;
    }

    public void setHotelID(long hotelID) {
        this.hotelID = hotelID;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", price=" + price +
                ", roomClass=" + roomClass +
                ", HotelID= " + hotelID +
                '}';
    }
}