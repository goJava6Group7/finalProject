package com.goJava6Group7.finalProject.data.dataBase.impl;

import com.goJava6Group7.finalProject.LocalDateAdapter;
import com.goJava6Group7.finalProject.data.dao.impl.DaoHotel;
import com.goJava6Group7.finalProject.data.dao.impl.DaoReservation;
import com.goJava6Group7.finalProject.data.dao.impl.DaoRoom;
import com.goJava6Group7.finalProject.data.dao.impl.DaoUser;
import com.goJava6Group7.finalProject.data.dataBase.DataBaseManager;
import com.goJava6Group7.finalProject.entities.Hotel;
import com.goJava6Group7.finalProject.entities.Reservation;
import com.goJava6Group7.finalProject.entities.Room;
import com.goJava6Group7.finalProject.entities.User;
import com.goJava6Group7.finalProject.utils.IdUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@XmlJavaTypeAdapter(value = LocalDateAdapter.class)
@XmlRootElement(name = "XML_Database")
@XmlAccessorType(XmlAccessType.FIELD)
public final class DataBaseManagerXml implements DataBaseManager {
    @XmlElementWrapper(name = "rooms")
    @XmlElement(name = "room")
    private List<Room> rooms = new ArrayList<>();

    @XmlElementWrapper(name = "users")
    @XmlElement(name = "user")
    private List<User> users = new ArrayList<>();

    @XmlElementWrapper(name = "hotels")
    @XmlElement(name = "hotel")
    private List<Hotel> hotels = new ArrayList<>();

    @XmlElementWrapper(name = "reservations")
    @XmlElement(name = "reservation")
    private List<Reservation> reservations = new ArrayList<>();

    protected DataBaseManagerXml(){
    }

    public DataBaseManagerXml(List<Room> rooms, List<User> users, List<Hotel> hotels, List<Reservation> reservations) {
        this.rooms = rooms;
        this.users = users;
        this.hotels = hotels;
        this.reservations = reservations;
    }

    @Override
    public DaoHotel getDaoHotel() {
        return new DaoHotel(this.hotels);
    }

    @Override
    public DaoReservation getDaoReservation() {
        return new DaoReservation(this.reservations);
    }

    @Override
    public DaoRoom getDaoRoom() {
        return new DaoRoom(this.rooms);
    }

    @Override
    public DaoUser getDaoUser() {
        return new DaoUser(this.users);
    }

    @Override
    public boolean updateDatabase() {
        try {
            File file = new File("XML_Database");
            DataBaseManagerXml dataBaseManagerXml = new DataBaseManagerXml(this.rooms, this.users, this.hotels, this.reservations);
            JAXBContext jaxbContext = JAXBContext.newInstance(DataBaseManagerXml.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(dataBaseManagerXml,file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean initDB() {
        try {
            File file = new File("XML_Database");
            if (!file.exists()){
                System.err.println("XML_Database doesn't exist. XML_Database will create by default");
                createDefaultDatabase();
            }
            JAXBContext jaxbContext = JAXBContext.newInstance(DataBaseManagerXml.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            DataBaseManagerXml dataBaseManagerXml = (DataBaseManagerXml) unmarshaller.unmarshal(file);
            this.rooms = dataBaseManagerXml.rooms;
            this.users = dataBaseManagerXml.users;
            this.hotels = dataBaseManagerXml.hotels;
            this.reservations = dataBaseManagerXml.reservations;
            IdUtil.getInstance(dataBaseManagerXml);   // init IdUtil class for getting sequence id
        } catch (JAXBException  e) {
            e.printStackTrace();
        }
        return true;
    }

    private void createDefaultDatabase() {
        try {
            File file = new File("XML_Database");
            DataBaseManagerXml dataBaseManagerXml = new DataBaseManagerXml();
            JAXBContext jaxbContext = JAXBContext.newInstance(DataBaseManagerXml.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(dataBaseManagerXml,file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "DataBaseManagerXml{" +
                "rooms=" + rooms +
                ", users=" + users +
                ", hotels=" + hotels +
                ", reservations=" + reservations +
                '}';
    }
}
