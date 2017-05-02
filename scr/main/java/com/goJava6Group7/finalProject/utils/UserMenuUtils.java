package com.goJava6Group7.finalProject.utils;

import com.goJava6Group7.finalProject.controllers.ProjectController;
import com.goJava6Group7.finalProject.entities.Hotel;
import com.goJava6Group7.finalProject.entities.Room;
import com.goJava6Group7.finalProject.entities.User;
import com.goJava6Group7.finalProject.main.Menu;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import com.goJava6Group7.finalProject.controllers.ProjectController.*;

import static com.goJava6Group7.finalProject.controllers.ProjectController.isBooked;
import static com.goJava6Group7.finalProject.utils.ConsoleWorkerUtil.*;

/**
 * Created by guillaume on 4/30/17.
 */
public class UserMenuUtils {

    private UserMenuUtils () {}

    public static void createUser(){

        User newUser;
        String userName = "";
        String email = "";
        String password = "";
        String yn;

        boolean ok = false;

        while (!ok){
            System.out.println("Please enter your username");
            while(true){
                try{
                    userName = readStringFromConsole();
                    break;
                }catch (IOException e){
                    System.out.println("You entered a wrong input, please try again");
                    continue;
                }
            }
            System.out.println("Please enter your email");
            while(true){
                try{
                    email = readStringFromConsole();
                    break;
                }catch (IOException e){
                    System.out.println("You entered a wrong input, please try again");
                    continue;
                }
            }

            System.out.println("Please enter your password");
            while(true){
                try{
                    password = readStringFromConsole();
                    break;
                }catch (IOException e){
                    System.out.println("You entered a wrong input, please try again");
                    continue;
                }
            }

            System.out.println("\nHere is a summary of your data:");
            System.out.println("Username: " + userName);
            System.out.println("Email: " + email);

            System.out.println("If this is correct, please enter Y, else press any key");

            while(true){
                try{
                    yn = readStringFromConsole();
                    break;
                }catch (IOException e){
                    System.out.println("You entered a wrong input, please try again");
                    continue;
                }
            }
            if (yn.equalsIgnoreCase("Y")) ok = true;
        }

        newUser = new User(email, userName, password);

        System.out.println(newUser);
        // here save this user to the database (and check if user already exists)

    }

    public static void searchHotelByName(ProjectController controller){

        String hotelName = "";
        List<Room> rooms;

        System.out.println("Please enter the hotel name");
        while(true){
            try{
                hotelName = readStringFromConsole();
                break;
            }catch(IOException e) {
                continue;
            }
        }

        System.out.println("Here is a list of hotels matching your criteria: ");

        System.out.println(controller.findHotelByHotelName(hotelName));

        System.out.println("To book a room, please note the name of the hotel of your choice" +
                "and choose the book a room option in the main menu");

    }

    public static List<Room> searchHotelByCityDates(ProjectController controller){
        List<Room> rooms;
        Scanner scan = new Scanner(System.in);

        System.out.println("Please enter the city name");
        String cityName = scan.nextLine();
        LocalDate checkin = getCheckinDate();
        LocalDate checkout = getCheckoutDate(checkin);

        System.out.println("Here is a list of hotels with rooms available when you will be in " + cityName +
                " from " + checkin + " to " + checkout);

        System.out.println(controller.findHotelByCityDate(cityName, checkin, checkout));
        rooms = controller.findRoomByCityDate(cityName, checkin, checkout);

        System.out.println("To book a room, please note the name of the hotel of your choice" +
                "and choose the book a room option in the main menu");

        return rooms;
    }

    public static List<Room> searchRoomByCityDate(ProjectController controller) throws NoSuchElementException {

        Scanner scan = new Scanner(System.in);

        System.out.println("Please enter the city name");
        String cityName = scan.nextLine();
        LocalDate checkin = getCheckinDate();
        LocalDate checkout = getCheckoutDate(checkin);

        List<Room> rooms = controller.findRoomByCityDate(cityName,checkin, checkout);

        System.out.println("Here are the rooms available in " + cityName +
                " from " + checkin + " to " + checkout);

        System.out.println(rooms);

        return rooms;
    }

    public static List<Room> searchRoomByHotelDate(ProjectController controller) throws NoSuchElementException {

        Scanner scan = new Scanner(System.in);

        System.out.println("Please enter the hotel name");
        String hotelName = scan.nextLine();
        LocalDate checkin = getCheckinDate();
        LocalDate checkout = getCheckoutDate(checkin);

        List<Room> rooms = controller.findRoomByHotelDate(hotelName,checkin, checkout);

        System.out.println("Here are the rooms available in the hotel " + hotelName +
                " from " + checkin + " to " + checkout);

        System.out.println(rooms);

        return rooms;

    }

    public static void bookRoom(List<Room> rooms){

        System.out.println("Please enter the number of the room you would like to book" +
        "from  to ");
        System.out.println(rooms);

    }

}
