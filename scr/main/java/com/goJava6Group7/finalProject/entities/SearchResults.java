package com.goJava6Group7.finalProject.entities;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by guillaume on 5/3/17.
 */
public class SearchResults {

    private LocalDate checkin;
    private LocalDate checkout;
    List<Room> rooms;

    public SearchResults(LocalDate checkin, LocalDate checkout, List<Room> rooms) {
        this.checkin = checkin;
        this.checkout = checkout;
        this.rooms = rooms;
    }

    public LocalDate getCheckin() {
        return checkin;
    }

    public LocalDate getCheckout() {
        return checkout;
    }

    public List<Room> getRooms() {
        return rooms;
    }

}
