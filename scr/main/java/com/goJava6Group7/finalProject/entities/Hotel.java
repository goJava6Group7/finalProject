package scr.main.java.com.goJava6Group7.finalProject.entities;

import scr.main.java.com.goJava6Group7.finalProject.utils.IdUtil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

/**
 * Created by Igor on 13.04.2017.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Hotel extends Entity {
    @XmlElement
    private long id;
    @XmlElement
    private String name;
    @XmlElement
    private String city;
    @XmlElement
    private int rating;
    @XmlElement
    private List<Room> rooms;

    public Hotel() {
    }

    public Hotel(String name, String city, int rating) {
        this.id = IdUtil.getHotelId();
        this.name = name;
        this.city = city;
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Hotel)) return false;

        Hotel hotel = (Hotel) o;
        return hotel.id == this.id && hotel.name.equals(this.name)
                && hotel.city.equals(this.city) && hotel.rating == this.rating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, city, rating);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", rating=" + rating +
                ", rooms=" + rooms +
                '}';
    }
}
