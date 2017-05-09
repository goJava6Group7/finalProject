package com.goJava6Group7.finalProject.entities;

import com.goJava6Group7.finalProject.data.dataBase.DataBaseManager;
import com.goJava6Group7.finalProject.data.dataBase.impl.DataBaseManagerFactory;
import com.goJava6Group7.finalProject.utils.IdUtil;

import java.time.LocalDate;

/**
 * Created by Igor on 13.04.2017.
 */
public class EntityTest {

    public static void main(String[] args) {
        DataBaseManager dataBaseManager = DataBaseManagerFactory.getInstance();
        IdUtil.getInstance(dataBaseManager);
        Hotel hotel = new Hotel("Live Hostel","Kiev", 2);
        Room room = new Room(5,5, RoomClass.Business, hotel.getId());
        Room room2 = new Room(2,50, RoomClass.President, hotel.getId());
        Room room3 = new Room(3,150, RoomClass.Apartment, hotel.getId());
        User user = new User("John","Handsome","pswd");
        User user2 = new User("Jane","Beautiful wut wut","1234");
        User user3 = new User("Ivan","Dzen","qwerty");
        Hotel hotel2 = new Hotel("Zupa Hotel", "Sin City", 4);
        LocalDate checkIn = LocalDate.now();
        LocalDate checkOut = LocalDate.now();
//        Reservation reservation = new Reservation(user, room, checkIn, checkOut);
//        Reservation reservation2 = new Reservation(user2, room2, checkIn, checkOut);

        System.out.println(Room.getOutputHeader());
        System.out.print(room.getOutput());
        System.out.print(room2.getOutput());
        System.out.print(room3.getOutput());
        System.out.println();
        System.out.println(User.getOutputHeader());
        System.out.print(user.getOutput());
        System.out.print(user2.getOutput());
        System.out.print(user3.getOutput());
        System.out.println();
        System.out.println(Hotel.getOutputHeader());
        System.out.println(hotel.getOutput());
        System.out.println(hotel2.getOutput());
        System.out.println();
        System.out.println(Reservation.getOutputHeader());
//        System.out.println(reservation.getOutput());
//        System.out.println(reservation2.getOutput());



    }

}
