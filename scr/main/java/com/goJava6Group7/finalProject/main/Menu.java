package com.goJava6Group7.finalProject.main;

import com.goJava6Group7.finalProject.controllers.ProjectController;
import com.goJava6Group7.finalProject.entities.Hotel;
import com.goJava6Group7.finalProject.entities.Room;
import com.goJava6Group7.finalProject.entities.User;
import com.goJava6Group7.finalProject.exceptions.frontend.OutOfMenuRangeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by Igor on 13.04.2017.
 */
public class Menu {
    private ProjectController controller;
    private Session session;
    private BufferedReader br = null;

    public Menu(ProjectController controller, Session session) {
        this.controller = controller;
        this.session = session;
    }

    boolean exit = false;

    private void printHeader(){
        System.out.println("*******************************");
        System.out.println("|      Welcome to our         |");
        System.out.println("|   Booking Application       |");
        System.out.println("*******************************");
    }

    private void printUserMainMenu(){
        System.out.println("\nPlease make a selection");
        System.out.println("[1] Register");
        System.out.println("[2] Login"); // if logged in, show "logout"
        System.out.println("[3] Search / book a room");
        System.out.println("[4] Search a hotel");
        System.out.println("[5] Admin menu");
        System.out.println("[6] Exit");
    }

    private void printUserHotelMenu(){
        System.out.println("\nPlease make a selection");
        System.out.println("[1] Search hotel by name");
        System.out.println("[2] Search hotel by city and dates");
        System.out.println("[3] Go back to main menu");
    }

    private void printUserHotelResultsMenu(){
        System.out.println("\nPlease make a selection");
        System.out.println("[1] Book a room"); // for admin / users
        System.out.println("[2] Go back to hotel search");
        System.out.println("[3] Go back to main menu");
    }

    private void printUserRoomMenu(){
        System.out.println("\nPlease make a selection");
        System.out.println("[1] Search room by hotel and dates"); // for all
        System.out.println("[2] Search room by city and dates"); // for all
        System.out.println("[3] Go back to main menu");
    }

    private void printUserRoomResultsMenu(){
        System.out.println("\nPlease make a selection");
        System.out.println("[1] Book a room"); // for admin / users
        System.out.println("[2] Go back to room search");
        System.out.println("[3] Go back to main menu");
    }

    private void adminChooseDB(){
        System.out.println("Please make a selection");
        System.out.println("[1] Choose XML database");
        System.out.println("[2] Choose binary database"); // tell them that if they change DB, system will restart
    }

    private void printAdminMainMenu(){
        System.out.println("Please make a selection");
        System.out.println("[1] Choose database");
        System.out.println("[2] Add a hotel"); // constructor with all fields: name, city,
        System.out.println("[3] Add a room"); // first find hotel, then constructor with all fields,
        System.out.println("[4] Update or delete a hotel"); // do like we do for users
        System.out.println("[5] Update or delete a room");
        System.out.println("[6] Find and update a user"); // search user by userName
        System.out.println("[7] Back to main menu");
    }

    private void addRoom(){
        System.out.println("Please enter the hotel in which you want to add a room");
        //search hotel method; not void but Hotel. User search not void, but string; and admin search is Hotel.
    }

    private void searchUser(){
        System.out.println("Please enter the login of the user");
        //search user method; not void but User. User search not void, but string; and admin search is Hotel.
        // two points: update user, delete user. new menu with 3 points: update user, delete user, go back to admin menu
        // do not update / delete admins: so in search, if it is admin, then return: "you cannot update or delete admins"

    }


    public void runMenu(){

        printHeader();
        while(!exit){
            printUserMainMenu();
            performActionUserMainMenu(getMenuInput(1,6));
        }

    }

    private int getMenuInput(int min, int max){
        int choice = 0;
        Scanner scan = new Scanner(System.in);

        while (choice < min || choice > max){
            try{
                System.out.print("\nEnter your selection");
                choice = Integer.parseInt(scan.nextLine());
                if (choice < min || choice > max) throw new OutOfMenuRangeException();

            } catch (NumberFormatException | OutOfMenuRangeException e){
                System.out.println("Invalid selection, please try again");
            }
        }
        return choice;
    }

    private void performActionUserMainMenu(int choice){
        switch(choice){
            case 1:
                createUser();
                break;
            case 2:
                //login
                break;
            case 3:
                printUserRoomMenu();
                performActionUserRoomMenu(getMenuInput(1,3));
                break;
            case 4:
                printUserHotelMenu();
                performActionUserHotelMenu(getMenuInput(1,3));
                break;
            case 5:
                // login
                printAdminMainMenu();
                //performActionAdminMainMenu();
            case 6:
                exit = true;
                System.out.println("Thank you for using our application");
                // default:
                //     System.out.println("An unknown error has occurred");
        }
    }

    private void performActionUserHotelMenu(int choice){
        switch(choice){
            case 1:
                searchHotelByName();
                break;
            case 2:
                searchHotelByCityDates();
                break;
            case 3:
                break;
            // default:
            //     System.out.println("An unknown error has occurred");
        }

    }

    private void performActionUserRoomMenu(int choice){
        switch (choice){
            case 1:
                searchRoomHotelDate();
                break;
            case 2:
                searchRoomCityDate();
                break;
            case 3:
                break;
        }
    }

    private void performActionUserHotelResultsMenu(int choice){
        switch (choice){
            case 1:
                bookRoom();
                break;
            case 2:
                printUserHotelMenu();
                performActionUserHotelMenu(getMenuInput(1,3));
                break;
            case 3:
                break;
        }
    }

    private void performActionUserRoomResultsMenu(int choice){
        switch (choice){
            case 1:
                bookRoom();
                break;
            case 2:
                printUserRoomMenu();
                performActionUserRoomMenu(getMenuInput(1,3));
                break;
            case 3:
                break;
        }
    }

    private void createUser(){

        User newUser;
        String userName = "";
        String email = "";
        String password = "";
        String yn;

        Scanner scan = new Scanner(System.in);

        boolean ok = false;

        while (!ok){
            System.out.println("Please enter your username");
            userName = scan.nextLine();
            System.out.println("Please enter your email");
            email = scan.nextLine();
            System.out.println("Please enter your password");
            password = scan.nextLine();

            System.out.println("\nHere is a summary of your data:");
            System.out.println("Username: " + userName);
            System.out.println("Email: " + email);

            System.out.println("If this is correct, please enter Y, else press any key");
            yn = scan.nextLine();
            if (yn.equalsIgnoreCase("Y")) ok = true;
        }

        newUser = new User(email, userName, password);

        System.out.println(newUser);
        // here save this user to the database (and check if user already exists)

    }

    private void searchHotelByName(){

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

        // here call controller function to search for hotel with hotelName

        System.out.println("Here is a list of hotels matching your criteria: ");

        //printUserHotelResultsMenu();
        //performActionUserHotelResultsMenu(getMenuInput(1,3));
    }

    private void searchHotelByCityDates(){
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

        System.out.println("Here is a list of hotels with rooms available when you will be in " + cityName +
                " from " + checkin + " to " + checkout);

        // here it would be nice to have a function so that user can enter hotel number and book room.
        // it should be done easily with an array that would be passed to the hotelresultsmenu.

        printUserHotelResultsMenu();
        performActionUserHotelResultsMenu(getMenuInput(1,3));

    }

    private void searchRoomHotelDate(){
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

        printUserRoomResultsMenu();
        performActionUserRoomResultsMenu(getMenuInput(1,3));
    }

    private void searchRoomCityDate() {
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

        printUserRoomResultsMenu();
        performActionUserRoomResultsMenu(getMenuInput(1,3));

    }

    private void bookRoom(){

        System.out.println("Please enter the number of the room you would like to book");


    }


//    TODO(Answer2)Эти методы можно вынести в отдельный утильный класс ConsoleWorkerUtil
    private int readIntFromConsole() throws IOException {

        br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine(); //throw IOException
        return Integer.parseInt(str);//throw NumberFormatException
//        Scanner scanner = new Scanner(System.in);
//        return scanner.nextInt();
    }

    private String readStringFromConsole() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine(); //throw IOException
    }

    private void printReadIntFromConsoleException(int number) {
        System.out.println("You typed something strange. " +
                "And we want a number from 1 to " + number + ". Please, type again.");
    }

}
