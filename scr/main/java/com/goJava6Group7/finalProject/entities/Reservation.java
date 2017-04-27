package scr.main.java.com.goJava6Group7.finalProject.entities;

import scr.main.java.com.goJava6Group7.finalProject.utils.IdUtil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Igor on 13.04.2017.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Reservation extends Entity {
    private long id;
    private User user;
    private Hotel hotel;
    private Room room;
    private Date checkIn;
    private Date checkOut;

    public Reservation() {
    }

    public Reservation(User user, Hotel hotel, Room room, Date checkIn, Date checkOut) {
        this.id = IdUtil.getReservationId();
        this.user = user;
        this.hotel = hotel;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Reservation)) return false;

        Reservation reservation = (Reservation) o;
        return reservation.hotel.equals(this.hotel) && reservation.room.equals(this.room)
                && reservation.checkIn.getTime() >= this.checkIn.getTime()
                    && reservation.checkIn.getTime() < this.checkOut.getTime();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, hotel, room, checkIn, checkOut);
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

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", user=" + user +
                ", hotel=" + hotel +
                ", room=" + room +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                '}';
    }
}
