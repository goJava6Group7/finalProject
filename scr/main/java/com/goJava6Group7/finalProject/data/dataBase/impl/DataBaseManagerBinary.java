package scr.main.java.com.goJava6Group7.finalProject.data.dataBase.impl;

import scr.main.java.com.goJava6Group7.finalProject.data.dao.impl.DaoHotel;
import scr.main.java.com.goJava6Group7.finalProject.data.dao.impl.DaoReservation;
import scr.main.java.com.goJava6Group7.finalProject.data.dao.impl.DaoRoom;
import scr.main.java.com.goJava6Group7.finalProject.data.dao.impl.DaoUser;
import scr.main.java.com.goJava6Group7.finalProject.data.dataBase.DataBaseManager;
import scr.main.java.com.goJava6Group7.finalProject.entities.Hotel;
import scr.main.java.com.goJava6Group7.finalProject.entities.Reservation;
import scr.main.java.com.goJava6Group7.finalProject.entities.Room;
import scr.main.java.com.goJava6Group7.finalProject.entities.User;
import scr.main.java.com.goJava6Group7.finalProject.utils.IdUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Igor on 14.04.2017.
 */
public final class DataBaseManagerBinary implements DataBaseManager, Serializable {

    private List<Room> rooms = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<Hotel> hotels = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    protected DataBaseManagerBinary(){}
    public DataBaseManagerBinary(List<Room> rooms, List<User> users, List<Hotel> hotels, List<Reservation> reservations) {
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
    public boolean initDB() {
        File file = new File("Binary_Database");
        if (!file.exists()) {
            System.err.println("Binary_Database doesn't exist. It will be created by default");
            createDefaultDatabase();
        }

        try {
            FileInputStream fis = new FileInputStream("Binary_Database");
            ObjectInputStream ois = new ObjectInputStream(fis);
            DataBaseManagerBinary dataBaseManagerBinary = (DataBaseManagerBinary) ois.readObject();
            this.rooms = dataBaseManagerBinary.rooms;
            this.users = dataBaseManagerBinary.users;
            this.hotels = dataBaseManagerBinary.hotels;
            this.reservations = dataBaseManagerBinary.reservations;
            IdUtil.getInstance(dataBaseManagerBinary);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;

    }

    @Override
    public boolean updateDatabase() {
        try {
            FileOutputStream fos = new FileOutputStream("Binary_Database");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            DataBaseManagerBinary dataBaseManagerBinary = new DataBaseManagerBinary(this.rooms, this.users, this.hotels, this.reservations);
            oos.writeObject(dataBaseManagerBinary);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;

    }

    private void createDefaultDatabase() {

        try {
            FileOutputStream fos = new FileOutputStream("Binary_Database");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            DataBaseManagerBinary dataBaseManagerBinary = new DataBaseManagerBinary();
            oos.writeObject(dataBaseManagerBinary);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return "DataBaseManagerBinary{" +
                "rooms=" + rooms +
                ", users=" + users +
                ", hotels=" + hotels +
                ", reservations=" + reservations +
                '}';
    }

}
