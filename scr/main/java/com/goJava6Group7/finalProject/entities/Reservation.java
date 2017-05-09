package com.goJava6Group7.finalProject.entities;

import com.goJava6Group7.finalProject.LocalDateAdapter;
import com.goJava6Group7.finalProject.utils.IdUtil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
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

        String output = String.format("%-4d \t %ty:%tm:%td \t %ty:%tm:%td %n", this.getId(), this.getCheckIn(), this.getCheckOut());

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

        if (id != that.id) return false;
        if (roomID != that.roomID) return false;
        if (!user.equals(that.user)) return false;
        if (!checkIn.equals(that.checkIn)) return false;
        return checkOut.equals(that.checkOut);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + user.hashCode();
        result = 31 * result + (int) (roomID ^ (roomID >>> 32));
        result = 31 * result + checkIn.hashCode();
        result = 31 * result + checkOut.hashCode();
        return result;
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
