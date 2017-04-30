package com.goJava6Group7.finalProject.main;

import com.goJava6Group7.finalProject.controllers.ProjectController;
import com.goJava6Group7.finalProject.exceptions.frontend.OutOfMenuRangeException;
import static com.goJava6Group7.finalProject.utils.UserMenuUtils.*;

import java.util.Scanner;

/**
 * Created by Igor on 13.04.2017.
 */
public class Menu {
    private ProjectController controller;
    private Session session;

    public Menu(ProjectController controller, Session session) {
        this.controller = controller;
        this.session = session;
    }

    boolean exit = false;

    public void runMenu() {

        printHeader();
        while (!exit) {
            printUserMainMenu();
            performActionUserMainMenu(getMenuInput(1, 6));
        }

    }

    private void printHeader() {
        System.out.println("*******************************");
        System.out.println("|      Welcome to our         |");
        System.out.println("|   Booking Application       |");
        System.out.println("*******************************");
    }

    // *********** USER MENU

    private void printUserMainMenu() {
        System.out.println("\nPlease make a selection");
        System.out.println("[1] Register");
        System.out.println("[2] Login"); // if logged in, show "logout"
        System.out.println("[3] Search / book a room");
        System.out.println("[4] Search a hotel");
        System.out.println("[5] Admin menu");
        System.out.println("[6] Exit");
    }

    private void printUserRoomMenu() {
        System.out.println("\nPlease make a selection");
        System.out.println("[1] Search room by hotel and dates"); // for all
        System.out.println("[2] Search room by city and dates"); // for all
        System.out.println("[3] Go back to main menu");
    }

    private void printUserRoomResultsMenu() {
        System.out.println("\nPlease make a selection");
        System.out.println("[1] Book a room"); // for admin / users
        System.out.println("[2] Go back to room search");
        System.out.println("[3] Go back to main menu");
    }

    private void printUserHotelMenu() {
        System.out.println("\nPlease make a selection");
        System.out.println("[1] Search hotel by name");
        System.out.println("[2] Search hotel by city and dates");
        System.out.println("[3] Go back to main menu");
    }

    private void printUserHotelResultsMenu() {
        System.out.println("\nPlease make a selection");
        System.out.println("[1] Book a room"); // for admin / users
        System.out.println("[2] Go back to hotel search");
        System.out.println("[3] Go back to main menu");
    }



    private int getMenuInput(int min, int max) {
        int choice = 0;
        Scanner scan = new Scanner(System.in);

        while (choice < min || choice > max) {
            try {
                System.out.print("\nEnter your selection");
                choice = Integer.parseInt(scan.nextLine());
                if (choice < min || choice > max) throw new OutOfMenuRangeException();

            } catch (NumberFormatException | OutOfMenuRangeException e) {
                System.out.println("Invalid selection, please try again");
            }
        }
        return choice;
    }

    private void performActionUserMainMenu(int choice) {
        switch (choice) {
            case 1:
                createUser();
                break;
            case 2:
                //login
                break;
            case 3:
                printUserRoomMenu();
                performActionUserRoomMenu(getMenuInput(1, 3));
                break;
            case 4:
                printUserHotelMenu();
                performActionUserHotelMenu(getMenuInput(1, 3));
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

    private void performActionUserRoomMenu(int choice) {
        switch (choice) {
            case 1:
                searchRoomHotelDate();
                printUserRoomResultsMenu();
                performActionUserRoomResultsMenu(getMenuInput(1,3));
                break;
            case 2:
                searchRoomCityDate();
                printUserRoomResultsMenu();
                performActionUserRoomResultsMenu(getMenuInput(1,3));
                break;
            case 3:
                break;
        }
    }

    private void performActionUserRoomResultsMenu(int choice) {
        switch (choice) {
            case 1:
                bookRoom();
                break;
            case 2:
                printUserRoomMenu();
                performActionUserRoomMenu(getMenuInput(1, 3));
                break;
            case 3:
                break;
        }
    }

    private void performActionUserHotelMenu(int choice) {
        switch (choice) {
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

    private void performActionUserHotelResultsMenu(int choice) {
        switch (choice) {
            case 1:
                bookRoom();
                break;
            case 2:
                printUserHotelMenu();
                performActionUserHotelMenu(getMenuInput(1, 3));
                break;
            case 3:
                break;
        }
    }



    // ************** ADMIN MENU

    private void printAdminMainMenu() {
        System.out.println("Please make a selection");
        System.out.println("[1] Choose database");
        System.out.println("[2] Add a hotel"); // constructor with all fields: name, city,
        System.out.println("[3] Add a room"); // first find hotel, then constructor with all fields,
        System.out.println("[4] Update or delete a hotel"); // do like we do for users
        System.out.println("[5] Update or delete a room");
        System.out.println("[6] Find and update a user"); // search user by userName
        System.out.println("[7] Back to main menu");
    }

    private void adminChooseDBMenu() {
        System.out.println("Please make a selection");
        System.out.println("[1] Choose XML database");
        System.out.println("[2] Choose binary database"); // tell them that if they change DB, system will restart
    }

    private void addRoom() {
        System.out.println("Please enter the hotel in which you want to add a room");
        //search hotel method; not void but Hotel. User search not void, but string; and admin search is Hotel.
    }

    private void searchUser() {
        System.out.println("Please enter the login of the user");
        //search user method; not void but User. User search not void, but string; and admin search is Hotel.
        // two points: update user, delete user. new menu with 3 points: update user, delete user, go back to admin menu
        // do not update / delete admins: so in search, if it is admin, then return: "you cannot update or delete admins"

    }

}
