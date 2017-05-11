package com.goJava6Group7.finalProject.entities;

import com.goJava6Group7.finalProject.LocalDateAdapter;
import com.goJava6Group7.finalProject.utils.IdUtil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;



/**
 * Created by Igor on 13.04.2017.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

public class Reservation extends Entity {
    @XmlElement
    private long id;
    @XmlElement
    private User user;
    @XmlElement
    private long roomID;
    @XmlElement
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class) private LocalDate checkIn;
    @XmlElement
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class) private LocalDate checkOut;

    public Reservation() {
    }

    public Reservation(User user, long roomID, LocalDate checkIn, LocalDate checkOut) {
        this.id = IdUtil.getReservationId();
        this.user = user;
        this.roomID = roomID;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    @Override
    public String getOutput(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String stringCheckin = this.getCheckIn().format(formatter);
        String stringCheckout= this.getCheckOut().format(formatter);

        String output = String.format("%-4s \t %-10s \t %-10s \t", this.getId(), stringCheckin, stringCheckout);

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

        Reservation reservation = (Reservation) o;

        return reservation.id == this.id;
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

    public long getRoomID() {
        return roomID;
    }

    public void setRoom(long roomID) {
        this.roomID = roomID;
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
                ", roomID=" + roomID +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                '}';
    }



}
