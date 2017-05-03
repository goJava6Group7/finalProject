package com.goJava6Group7.finalProject.main;

import com.goJava6Group7.finalProject.controllers.ProjectController;
import com.goJava6Group7.finalProject.entities.SearchResults;
import com.goJava6Group7.finalProject.entities.User;
import com.goJava6Group7.finalProject.exceptions.backend.BackendException;

import static com.goJava6Group7.finalProject.data.dataBase.impl.DataBaseManagerFactory.*;
import static com.goJava6Group7.finalProject.data.dataBase.impl.DataBaseManagerFactory.DataBaseManagerType.*;
import static com.goJava6Group7.finalProject.utils.ConsoleWorkerUtil.*;

import java.io.IOException;

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
            printGuestMainMenu();
            performActionGuestMainMenu(getMenuInput(1, 6));
        }

    }

    private void printHeader() {
        System.out.println("*******************************");
        System.out.println("|      Welcome to our         |");
        System.out.println("|   Booking Application       |");
        System.out.println("*******************************");
    }

    // *********** USER MENU

    private void printGuestMainMenu() {
        System.out.println("\nPlease make a selection");
        System.out.println("[1] Register");
        System.out.println("[2] Login"); // if logged in, show "logout"
        System.out.println("[3] Search / book a room");
        System.out.println("[4] Search a hotel");
        System.out.println("[5] Admin menu");
        System.out.println("[6] Exit");
    }

    private void printUserMainMenu() {
        System.out.println("\nPlease make a selection");
        System.out.println("[1] Update your profile");
        System.out.println("[2] Logout");
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



    private void performActionGuestMainMenu(int choice){
        switch(choice){
            case 1:
                controller.createUser();
                break;
            case 2:
                session = controller.login(session);
                if (session.isGuest()){
                    printGuestMainMenu();
                    performActionGuestMainMenu(getMenuInput(1,6));
                }else{
                    printUserMainMenu();
                    performActionUserMainMenu(getMenuInput(1,6));
                }
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

    private void performActionUserMainMenu(int choice) {
        switch (choice) {
            case 1:
                controller.updateUser(session.getUser());
                printUserMainMenu();
                performActionUserMainMenu(getMenuInput(1,6));
                break;
            case 2:
                controller.logout(session);
                printGuestMainMenu();
                performActionGuestMainMenu(getMenuInput(1,6));
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
        SearchResults results;
        switch (choice) {
            case 1:
                results = controller.findRoomByHotelDate();
                printUserRoomResultsMenu();
                performActionUserRoomResultsMenu(results, getMenuInput(1,3));
                break;
            case 2:
                results = controller.findRoomByCityDate();
                printUserRoomResultsMenu();
                performActionUserRoomResultsMenu(results, getMenuInput(1,3));
                break;
            case 3:
                break;
        }
    }

    private void performActionUserRoomResultsMenu(SearchResults results, int choice) {
        switch (choice) {
            case 1:
                controller.bookRoom(results);
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
        SearchResults results;
        switch (choice) {
            case 1:
                controller.findHotelByHotelName();
                break;
            case 2:
                results = controller.findHotelByCityDate();
                printUserHotelResultsMenu();
                performActionUserHotelResultsMenu(results, getMenuInput(1,3));
                break;
            case 3:
                break;
        }

    }

    private void performActionUserHotelResultsMenu(SearchResults results, int choice) {
        switch (choice) {
            case 1:
                controller.bookRoom(results);
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
     * TODO Какой-то "кривой" код. Переделать. Возможно взять login(Session) Гийома, но его метод возвращает Session
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
            case 2:
                addHotel();
            case 3: //TODO
            case 4:
            case 5:
            case 6:
            case 7:
            default:
        }

    }


    //TODO Упростить
    private void chooseTheDatabase() {

        adminChooseDBMenu();

        int choice;
        int choiceDB = 0;

        try {
            choice = readIntFromConsole();
            System.out.println("Are you sure you want to change database? " +
                    "If you do, we will restart the system");
            if (confirm()) {choiceDB = choice;}
            else adminMenu();
        } catch (Exception e) {
            printReadIntFromConsoleException(2);
            chooseTheDatabase();
        }

        switch (choiceDB) {
            case 1:
                try {
                    getDataBaseManager(XML).initDB();
                } catch (BackendException e) {
                    e.printStackTrace();
                }
            case 2:
                try {
                    getDataBaseManager(BINARY).initDB();
                } catch (BackendException e) {
                    e.printStackTrace();
                }
        }

    }

    public void adminChooseDBMenu() {
        System.out.println("Please choose the database you want to work with");
        System.out.println("[1] XML database");
        System.out.println("[2] Binary database"); // tell them that if they change DB, system will restart
    }

    /**
     * TODO Слишком запутано написала. Подумать над рефакторингом
     * Kontar Maryna:
     *
     * The method confirm something
     * @return true if 1 and false otherwise
     */
    private boolean confirm() {
        printConfirmMenu();
        try {
            if (readIntFromConsole() == 1)
                return true;
            else if (readIntFromConsole() == 2)
                return false;
        } catch (Exception e) {
            printReadIntFromConsoleException(2);
            confirm();
        }
    return false;
    }

    private void printConfirmMenu() {
        System.out.println("[1] Yes");
        System.out.println("[2] No");
    }


    //TODO Не сделано!!!
    private void addHotel() {
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