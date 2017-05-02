package scr.main.java.com.goJava6Group7.finalProject.entities;

import scr.main.java.com.goJava6Group7.finalProject.utils.IdUtil;
import scr.main.java.com.goJava6Group7.finalProject.entities.RoomClass;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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

    public Room() {
    }

    public Room(int capacity, int price, RoomClass roomClass) {
        this.id = IdUtil.getRoomId();
        this.capacity = capacity;
        this.price = price;
        this.roomClass = roomClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Room)) return false;

        Room room = (Room) o;
        return  room.id == this.id && room.capacity == this.capacity
                && room.price == this.price && room.roomClass.equals(this.roomClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, capacity, price, roomClass);
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

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", price=" + price +
                ", roomClass=" + roomClass +
                '}';
    }
}