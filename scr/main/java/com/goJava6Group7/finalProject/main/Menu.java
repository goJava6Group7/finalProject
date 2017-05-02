package com.goJava6Group7.finalProject.main;

import com.goJava6Group7.finalProject.controllers.ProjectController;
import com.goJava6Group7.finalProject.entities.Room;
import com.goJava6Group7.finalProject.entities.User;
import com.goJava6Group7.finalProject.exceptions.frontend.OutOfMenuRangeException;

import static com.goJava6Group7.finalProject.utils.ConsoleWorkerUtil.*;
import static com.goJava6Group7.finalProject.utils.UserMenuUtils.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
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

    public void printUserRoomResultsMenu() {
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

    public void printUserHotelResultsMenu() {
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
        List<Room> rooms;
        switch (choice) {
            case 1:
                rooms = searchRoomByHotelDate(controller);
                printUserRoomResultsMenu();
                performActionUserRoomResultsMenu(rooms, getMenuInput(1, 3));
                break;
            case 2:
                rooms = searchRoomByCityDate(controller);
                printUserRoomResultsMenu();
                performActionUserRoomResultsMenu(rooms, getMenuInput(1, 3));
                break;
            case 3:
                break;
        }
    }

    public void performActionUserRoomResultsMenu(List<Room> rooms, int choice) {
        switch (choice) {
            case 1:
                bookRoom(rooms);
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
        List<Room> rooms;
        switch (choice) {
            case 1:
                searchHotelByName(controller);
                break;
            case 2:
                rooms = searchHotelByCityDates(controller);
                printUserHotelResultsMenu();
                performActionUserHotelResultsMenu(rooms, getMenuInput(1, 3));
                break;
            case 3:
                break;
            // default:
            //     System.out.println("An unknown error has occurred");
        }

    }

    public void performActionUserHotelResultsMenu(List<Room> rooms, int choice) {
        switch (choice) {
            case 1:
                bookRoom(rooms);
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

    public void adminMenu() {
        if (ifAdminLogin()) {
            printAdminMainMenu();
            performActionAdminMainMenu();
        } else {
            System.out.println("You do not have administrator rights.");
            runMenu();
        }
    }


    /**
     * TODO Какой-то "кривой" код. Переделать.
     * Kontar Maryna:
     * The method ask for login and password and return true if they belong to admin
     *
     * @return true if it's admin want to login and false otherwise
     */
    private boolean ifAdminLogin() {
        try {
            return adminLoginAndPasswordVerification(askLogin(), askPassword());
        } catch (IOException e) {
            System.out.println("These login or password are wrong. Try again.");
            ifAdminLogin();
        }
        return false;
    }



    /**
     * TODO Уточнить по проверке на админа!!! И о том, что должна возвращать функция loginAndPasswordVerification
     * TODO Использую для проверки роли user. Если user - администратор, то устанавливаю для него новую сессию
     * Kontar Maryna:
     * The method checks if login and password belongs to the admin
     *
     * @param login
     * @param password
     * @return true if login and password belongs to the admin
     * and false if user with the appropriate login and password doesn't exist or user isn't admin
     */
    private boolean adminLoginAndPasswordVerification(String login, String password) {
        User user = controller.loginAndPasswordVerification(login, password);
        //TODO ПРОВЕРКА НА АДМИНА ПРАВИЛЬНАЯ??? ИЛИ session.isAdmin()?
        if (user != null && user.getRole().equals(User.Role.ADMIN)) {
            assignmentSessionForAdmin(user);
            return true;
        }
        return false;
    }

    /**
     * TODO Игорю на проверку. Использую для того, чтобы установить сессию для админа,
     * TODO если при проверке логина, пароля и роли это оказался администратор
     * Kontar Maryna:
     * <p>
     * The method assign session for admin
     *
     * @param admin
     */
    private void assignmentSessionForAdmin(User admin) {
        session = new Session(admin);
        session.setAdmin(true);
    }

    public void printAdminMainMenu() {
        System.out.println("Please make a selection");
        System.out.println("[1] Choose database");
        System.out.println("[2] Add a hotel"); // constructor with all fields: name, city,
        System.out.println("[3] Add a room"); // first find hotel, then constructor with all fields,
        System.out.println("[4] Update or delete a hotel"); // do like we do for users
        System.out.println("[5] Update or delete a room");
        System.out.println("[6] Find and update a user"); // search user by userName
        System.out.println("[7] Back to main menu");
    }

    private void performActionAdminMainMenu() {
        int choice = 0;
        try {
            choice = readIntFromConsole();
        } catch (Exception e) {
            printReadIntFromConsoleException(7);
            printAdminMainMenu();
            performActionAdminMainMenu();
        }
        switch (choice) {
            case 1:
                chooseTheDatabase();
            case 2: //TODO
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                default:
        }

    }


    private void chooseTheDatabase() {
        adminChooseDBMenu();
        int choiceDB = 0;
        try {
            choiceDB = readIntFromConsole();
        } catch (Exception e) {
            printReadIntFromConsoleException(2);
            chooseTheDatabase();
        }
        switch (choiceDB) {
            case 1:
                //TODO;
        }

    }




    public void adminChooseDBMenu() {
        System.out.println("Please make a selection");
        System.out.println("[1] Choose XML database");
        System.out.println("[2] Choose binary database"); // tell them that if they change DB, system will restart
    }

    public void addRoom() {
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
