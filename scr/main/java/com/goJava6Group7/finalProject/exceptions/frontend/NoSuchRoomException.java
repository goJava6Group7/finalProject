package com.goJava6Group7.finalProject.exceptions.frontend;

import com.goJava6Group7.finalProject.controllers.ProjectController;
import com.goJava6Group7.finalProject.data.dataBase.DataBaseManager;
import com.goJava6Group7.finalProject.entities.Hotel;
import com.goJava6Group7.finalProject.entities.Room;

import java.util.List;

/**
 * Created by guillaume on 4/23/17.
 */
public class NoSuchRoomException extends Exception {
    private DataBaseManager dbm;
    public NoSuchRoomException(String hotelName){
        System.err.println("There is no room with this name in this hotel.");

        // get hotel

        ProjectController controller = new ProjectController(dbm);
        Hotel hotelRoom = controller.findHotelByHotelName(hotelName);
        List<Room> rooms = controller.findRoomsInHotel(hotelRoom);

        System.out.println(rooms);

    }
}
