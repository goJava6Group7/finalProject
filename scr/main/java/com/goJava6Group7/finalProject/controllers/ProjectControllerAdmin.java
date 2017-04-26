package com.goJava6Group7.finalProject.controllers;

import com.goJava6Group7.finalProject.data.dataBase.DataBaseManager;
import com.goJava6Group7.finalProject.entities.Hotel;
import com.goJava6Group7.finalProject.entities.Room;
import com.goJava6Group7.finalProject.exceptions.frontend.RoomAlreadyExistsException;
import com.goJava6Group7.finalProject.utils.IdUtil;



import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by guillaume on 4/23/17.
 */
public class ProjectControllerAdmin {

    // to add, delete and update rooms and hotels

    /* Room methods: add, delete, update
        Add room will
        1) create a unique ID;
        2) prompt the user to enter data about the room;
        3) create a room with constructor;
        4) save the room to the database using DAO.

      Delete room will:
        1) Look for the room in question using the search room function;
        2) Use DAO to delete this room.

       Update a room will:
       1) Look for the room in question using the search room function;
       2) Ask the user to re-enter the information about the room;
       3) Save the room in the DB using DAO
     */

    // connect to database
    //TODO(замечания) - в рабочей версии выносить отдельно переменные НЕ нужно
    private DataBaseManager dbManager;
    private List<Hotel> allHotels = dbManager.getDaoHotel().getAll();
    private List<Room> allRooms = dbManager.getDaoRoom().getAll();

    public ProjectControllerAdmin(DataBaseManager dbManager) {
        this.dbManager = dbManager;
    }


    // Add a room
    //TODO(замечания) - зименить сигнатуру на public void AdminCreatesRoom (Room room, Hotel hotel)
    //TODO(замечания) - убрать работу с консолью с метода контроллера
    public void AdminCreatesRoom (){

        // public Room(String id, String name, int numberOfPersons, int price, String hotel)

        // create unique ID
        String roomId = IdUtil.IdGenerator();

        /* prompt user to enter different values:

        check if hotel exists
        check if room name already exists

        */

        // get hotel name
        Scanner scanner = new Scanner(System.in);
        String hotelName;

        while (true){
            try {
                System.out.println("Please enter the hotel of this new room");
                hotelName = scanner.next();

                // check if hotel exists and throw exception if hotel does not exist
                if (allHotels.stream()
                        .filter(o -> o.getHotelName().equals(hotelName))
                        .findFirst()
                        .isPresent()){
                    break;
                } else throw new NoSuchElementException();

            } catch (NoSuchElementException e) {
                System.err.println("There is no such hotel in our database, please try again.");
                continue;
            }
        }

        // get room name
        String roomName;

        // check if room name already exists
        while (true){
            try {
                System.out.println("Please enter the new room's name");
                roomName = scanner.next();

                // check if room exists and throw exception if room already exists in hotel
                if (allRooms.stream()
                        .filter(o -> o.getName().equals(roomName) && o.getHotel().equals(hotelName))
                        .findFirst()
                        .isPresent()){
                    throw new RoomAlreadyExistsException(
                            allRooms.stream()
                                    .filter(o -> o.getName().equals(roomName) && o.getHotel().equals(hotelName))
                                    .findFirst()
                                    .get()
                    );
                } else break;

            } catch (RoomAlreadyExistsException e) {
                continue;
            }
        }

        // get guest number
        System.out.println("Please enter the number of guests for this room");
        int numberOfGuests = scanner.nextInt();

        // get room price
        System.out.println("Please enter the price per night for this room");
        int price = scanner.nextInt();

        // Call constructor to create new room

        Room newRoom = new Room(roomId, roomName, numberOfGuests, price, hotelName);

        // save room in DB using DAO

        dbManager.getDaoRoom().create(newRoom);
        System.out.println("You have successfully created the following room: " + newRoom);
    }

}
