package com.goJava6Group7.finalProject.utils;

import com.goJava6Group7.finalProject.controllers.ProjectController;
import com.goJava6Group7.finalProject.entities.User;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

import com.goJava6Group7.finalProject.controllers.ProjectController.*;

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

    public static void searchHotelByCityDates(ProjectController controller){
        Scanner scan = new Scanner(System.in);

        System.out.println("Please enter the city name");
        String cityName = scan.nextLine();
        LocalDate checkin = getCheckinDate();
        LocalDate checkout = getCheckoutDate(checkin);

        System.out.println("Here is a list of hotels with rooms available when you will be in " + cityName +
                " from " + checkin + " to " + checkout);

        System.out.println(controller.findHotelByCityDate(cityName, checkin, checkout));

        System.out.println("To book a room, please note the name of the hotel of your choice" +
                "and choose the book a room option in the main menu");

        // here it would be nice to have a function so that user can enter hotel number and book room.
        // it should be done easily with an array that would be passed to the hotelresultsmenu.

    }

    public static void searchRoomHotelDate(){
        Scanner scan = new Scanner(System.in);

        System.out.println("Please enter the hotel name");
        String hotelName = scan.nextLine();
        System.out.println("Please enter your check-in date");
        String checkin = scan.nextLine();
        System.out.println("Please enter your check-out date");
        String checkout = scan.nextLine();

        System.out.println("Here is a list of rooms that are available in " + hotelName +
                " from " + checkin + " to " + checkout);

        System.out.println("Do you want to book a room?");

        // here again, a function to book one room in the list using an array

    }

    public static void searchRoomCityDate() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Please enter the city name");
        String cityName = scan.nextLine();
        System.out.println("Please enter your check-in date");
        String checkin = scan.nextLine();
        System.out.println("Please enter your check-out date");
        String checkout = scan.nextLine();

        // we need a logic to check the dates (format, and logic: checkin before checkout, and checkout not
        // more than 30 days after checkin
        // here call controller function to search for hotels in given cities and available at these dates

        System.out.println("Here is a list of rooms available when you will be in " + cityName + " from " + checkin
                + " to " + checkout);

        // here again, a function to book one room in the list using an array

    }

    public static void bookRoom(){

        System.out.println("Please enter the number of the room you would like to book");


    }

}
