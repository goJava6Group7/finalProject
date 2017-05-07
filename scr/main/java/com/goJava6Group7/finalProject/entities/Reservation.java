package com.goJava6Group7.finalProject.entities;

import com.goJava6Group7.finalProject.utils.IdUtil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Created by Igor on 13.04.2017.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Reservation extends Entity {
    private long id;
    private User user;
    private Room room;
    private LocalDate checkIn;
    private LocalDate checkOut;

    public Reservation() {
    }

    public Reservation(User user, Room room, LocalDate checkIn, LocalDate checkOut) {
        this.id = IdUtil.getReservationId();
        this.user = user;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    @Override
    public String getOutput(){

        String output = String.format("%-4d \t %-10tD \t %-10tD", this.getId(), this.getCheckIn(), this.getCheckOut());

        return output;

    }

//    @Override
    public static String getOutputHeader() {

        String header = String.format("%-4s \t %-10s \t %-10s \t ", "id", "check in", "check out");

        return header;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservation that = (Reservation) o;

        return (id == that.id);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public long getId() {
        return id;
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

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", user=" + user +
                ", room=" + room +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                '}';
    }
}
