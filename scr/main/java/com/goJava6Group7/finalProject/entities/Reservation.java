package com.goJava6Group7.finalProject.entities;

import java.time.LocalDate;

/**
 * Created by Igor on 13.04.2017.
 */
public class Reservation extends Entity {

    //TODO Kontar Maryna changes these for  ProjectController   reserveRoom
    //TODO(Answer) поле Hotel лишнее(ссылка на Hotel должна быть в обьекте Room)
    private User user;
    private Room room;
    private LocalDate dateOfArrival;
    private LocalDate dateOfDeparture;

    public Reservation(User user, Room room, LocalDate dataOfArrival, LocalDate dateOfDeparture) {
        this.user = user;
        this.room = room;
        this.dateOfArrival = dataOfArrival;
        this.dateOfDeparture = dateOfDeparture;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getDateOfArrival() {
        return dateOfArrival;
    }

    public void setDateOfArrival(LocalDate dateOfArrival) {
        this.dateOfArrival = dateOfArrival;
    }

    public LocalDate getDateOfDeparture() {
        return dateOfDeparture;
    }

    public void setDateOfDeparture(LocalDate dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }
}
