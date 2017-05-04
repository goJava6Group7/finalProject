package com.goJava6Group7.finalProject.entities;

import com.goJava6Group7.finalProject.data.dataBase.DataBaseManager;
import com.goJava6Group7.finalProject.data.dataBase.impl.DataBaseManagerFactory;
import com.goJava6Group7.finalProject.utils.IdUtil;
/**
 * Created by Igor on 13.04.2017.
 */
public class EntityTest {

    public static void main(String[] args) {
        DataBaseManager dataBaseManager = DataBaseManagerFactory.getInstance();
        IdUtil.getInstance(dataBaseManager);
        Room room = new Room(5,5, RoomClass.Business);
        Room room2 = new Room(2,50, RoomClass.President);
        Room room3 = new Room(3,150, RoomClass.Apartment);
        Hotel hotel = new Hotel("Live Hostel","Kiev", 2);
        User user = new User("John","Handsome","pswd");
        User user2 = new User("Jane","Beautiful wut wut","1234");
        User user3 = new User("Ivan","Dzen","qwerty");
        Hotel hotel2 = new Hotel("Zupa Hotel", "Sin City", 4);

//        System.out.print(hotel2.getOutput());
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



    }

}
