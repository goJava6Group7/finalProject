package com.goJava6Group7.finalProject.entities;

import java.util.List;

/**
 * Created by Igor on 13.04.2017.
 */
public class Room extends Entity {

    private String id;
    private String name;
    private int numberOfPersons;
    private int price;
    private Hotel hotel;
    private List<Reservation> bookings;

    //TODO(замечания) - id должно генерироваться, а НЕ присваиватся
    public Room(String id, String name, int numberOfPersons, int price, Hotel hotel) {
        this.id = id;
        this.name = name;
        this.numberOfPersons = numberOfPersons;
        this.price = price;
        this.hotel = hotel;
    }

    // equals and hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (numberOfPersons != room.numberOfPersons) return false;
        if (price != room.price) return false;
        if (!id.equals(room.id)) return false;
        if (!name.equals(room.name)) return false;
        if (!hotel.equals(room.hotel)) return false;
        return bookings != null ? bookings.equals(room.bookings) : room.bookings == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + numberOfPersons;
        result = 31 * result + price;
        result = 31 * result + hotel.hashCode();
        result = 31 * result + (bookings != null ? bookings.hashCode() : 0);
        return result;
    }


    // getter and setter


    public String getId() {
        return id;
    }

    public int getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(int numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public List<Reservation> getBookings() {
        return bookings;
    }

    public void setBookings(List<Reservation> bookings) {
        this.bookings = bookings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}