package com.goJava6Group7.finalProject.main;

import com.goJava6Group7.finalProject.controllers.ProjectController;
import com.goJava6Group7.finalProject.utils.ConsoleWorkerUtil;
import com.goJava6Group7.finalProject.entities.*;
import com.goJava6Group7.finalProject.exceptions.frontend.*;
import com.goJava6Group7.finalProject.entities.Room.RoomParameters;
import com.goJava6Group7.finalProject.entities.Hotel.HotelParameters;
import com.goJava6Group7.finalProject.entities.User.UserParameters;

import java.util.*;

import static com.goJava6Group7.finalProject.entities.Hotel.HotelParameters.*;
import static com.goJava6Group7.finalProject.entities.Room.RoomParameters.*;
import static com.goJava6Group7.finalProject.entities.User.UserParameters.*;
import static com.goJava6Group7.finalProject.entities.User.Role.*;
import static com.goJava6Group7.finalProject.utils.ConsoleWorkerUtil.*;


public class Menu {
    private ProjectController controller;
    private Session session;

    public Menu(ProjectController controller, Session session) {
        this.controller = controller;
        this.session = session;
    }

    boolean exit = false;

    /**
     * Run Booking Application.
     *
     * @see #printHeader()
     * @see #printGuestMainMenu()
     * @see #performActionGuestMainMenu(int)
     */
    public void runMenu() {

        printHeader();
        while (!exit) {
            printGuestMainMenu();
            performActionGuestMainMenu(getMenuInput(1, 6));
        }
    }

    /**
     * Prints out a header of the Booking Application.
     *
     * @see #runMenu()
     */
    private void printHeader() {
        System.out.println("*******************************");
        System.out.println("|      Welcome to our         |");
        System.out.println("|   Booking Application       |");
        System.out.println("*******************************");
    }

    // ***************************** USER MENU ************************************

    /**
     * Prints out a Guest Main Menu.
     *
     * @see #runMenu()
     */
    private void printGuestMainMenu() {
        System.out.println("\nPlease make a selection");
        System.out.println("[1] Register");
        System.out.println("[2] Login"); // if logged in, show "logout"
        System.out.println("[3] Search / book a room");
        System.out.println("[4] Search a hotel");
        System.out.println("[5] Admin menu");
        System.out.println("[6] Exit");
    }

    /**
     * Prints out a User Main Menu.
     *
     * @see #runMenu()
     */
    private void printUserMainMenu() {
        System.out.println("\nPlease make a selection");
        System.out.println("[1] See / update your profile and bookings");
        System.out.println("[2] Logout");
        System.out.println("[3] Search / book a room");
        System.out.println("[4] Search a hotel");
        System.out.println("[5] Admin menu");
        System.out.println("[6] Exit");
    }

    /**
     * Prints out a User Menu for searching of the rooms.
     *
     * @see #runMenu()
     * @see #printUserMainMenu()
     */
    private void printUserRoomMenu() {
        System.out.println("\nPlease make a selection");
        System.out.println("[1] Search room by hotel and dates"); // for all
        System.out.println("[2] Search room by city and dates"); // for all
        System.out.println("[3] Go back to main menu");
    }

    /**
     * Prints out a User Menu, prompting the user to book one of the found rooms.
     *
     * @see #runMenu()
     * @see #printUserRoomMenu()
     */
    private void printUserRoomResultsMenu() {
        System.out.println("\nPlease make a selection");
        System.out.println("[1] Book a room"); // for admin / users
        System.out.println("[2] Go back to room search");
        System.out.println("[3] Go back to main menu");
    }

    /**
     * Prints out a User Menu for searching of the hotels.
     *
     * @see #runMenu()
     * @see #printUserMainMenu()
     */
    private void printUserHotelMenu() {
        System.out.println("\nPlease make a selection");
        System.out.println("[1] Search hotel by name");
        System.out.println("[2] Search hotel by city and dates");
        System.out.println("[3] Go back to main menu");
    }

    /**
     * Prints out a User Menu, prompting the user to book a room in one of the found hotels.
     *
     * @see #runMenu()
     * @see #printUserHotelMenu()
     */
    private void printUserHotelResultsMenu() {
        System.out.println("\nPlease make a selection");
        System.out.println("[1] Book a room"); // for admin / users
        System.out.println("[2] Go back to hotel search");
        System.out.println("[3] Go back to main menu");
    }

    /**
     * Processes the selection made by user in Guest Main Menu,
     * calling the respective methods required to perform action selected by user.
     *
     * @see ProjectController#createUser()
     * @see ProjectController#login(Session)
     * @see Session#isGuest()
     * @see #printUserMainMenu()
     * @see #performActionUserMainMenu(int)
     * @see ProjectController#updateDB()
     */
    private void performActionGuestMainMenu(int choice) {
        switch (choice) {
            case 1:
                controller.createUser();
                break;
            case 2:
                session = controller.login(session);
                if (session.isGuest()) {
                    printGuestMainMenu();
                    performActionGuestMainMenu(getMenuInput(1, 6));
                } else {
                    printUserMainMenu();
                    performActionUserMainMenu(getMenuInput(1, 6));
                }
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
                if (isAdmin()) {
                    adminMenu();
                } else {
                    System.out.println("You do not have administrator rights. Login as administrator.");
                    printUserMainMenu();
                    performActionUserMainMenu(readIntToMaxNum(6));
                }
                break;
            case 6:
                exit = true;
                controller.updateDB();
                System.out.println("Thank you for using our application");
                // default:
                //     System.out.println("An unknown error has occurred");
        }
    }

    /**
     * Processes the selection made by user in User Main Menu, calling the respective methods required to perform action selected by user. .
     *
     * @see #printUserBookingUpdateMenu()
     * @see ProjectController#getUsersBookings(User)
     * @see Session#getUser()
     * @see ProjectController#logout(Session)
     * @see #printUserRoomMenu()
     * @see #printUserHotelMenu()
     * @see #performActionUserRoomMenu(int)
     * @see #performActionUserHotelMenu(int)
     * @see ProjectController#updateDB()
     */
    private void performActionUserMainMenu(int choice) {
        switch (choice) {
            case 1:
                printUserBookingUpdateMenu();
                int max;
                if (!controller.getUsersBookings(session.getUser()).isEmpty()) {
                    max = 4;
                } else {
                    max = 2;
                }
                performActionsUserBookingUpdateMenu(getMenuInput(1, max));
                //controller.updateUser(session.getUser());
                //printUserMainMenu();
                //performActionUserMainMenu(getMenuInput(1, 6));
                break;
            case 2:
                controller.logout(session);
                printGuestMainMenu();
                performActionGuestMainMenu(getMenuInput(1, 6));
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
                if (isAdmin()) {
                    adminMenu();
                } else {
                    System.out.println("You do not have administrator rights. Login as administrator.");
                    printUserMainMenu();
                    performActionUserMainMenu(readIntToMaxNum(6));
                }
                break;
            case 6:
                controller.updateDB();
                exit = true;
                controller.updateDB();
                System.out.println("Thank you for using our application");
                // default:
                //     System.out.println("An unknown error has occurred");
        }
    }


    private void performActionUserRoomMenu(int choice) {
        SearchResults results = null;
        switch (choice) {
            case 1:
                try {
                    results = controller.findRoomByHotelDate();
                    if (results == null | results.getRooms().size() == 0) throw new NullSearchResultsException("");
                } catch (NullSearchResultsException | NullPointerException e) {
                    System.out.println("There is no room matching your criteria");
                }
                if (results == null) {
                    if (!session.isGuest()) {
                        printUserMainMenu();
                        performActionUserMainMenu(getMenuInput(1, 6));
                        break;
                    } else break;
                } else if (results.getRooms().size() == 0) {
                    printUserMainMenu();
                    performActionUserMainMenu(getMenuInput(1, 6));
                    break;
                } else {
                    printUserRoomResultsMenu();
                    performActionUserRoomResultsMenu(results, getMenuInput(1, 3));
                    break;
                }
            case 2:
                try {
                    results = controller.findRoomByCityDate();
                    if (results == null | results.getRooms().size() == 0) throw new NullSearchResultsException("");
                } catch (NullSearchResultsException | NullPointerException e) {
                    System.out.println("There is no room matching your criteria");
                }
                if (results == null) {
                    if (!session.isGuest()) {
                        printUserMainMenu();
                        performActionUserMainMenu(getMenuInput(1, 6));
                        break;
                    } else break;
                } else if (results.getRooms().size() == 0) {
                    if (!session.isGuest()) {
                        printUserMainMenu();
                        performActionUserMainMenu(getMenuInput(1, 6));
                        break;
                    } else break;
                } else {
                    printUserRoomResultsMenu();
                    performActionUserRoomResultsMenu(results, getMenuInput(1, 3));
                    break;
                }
            case 3:
                if (!session.isGuest()) {
                    printUserMainMenu();
                    performActionUserMainMenu(getMenuInput(1, 6));
                    break;
                } else break;
        }
    }

    private void performActionUserRoomResultsMenu(SearchResults results, int choice) {
        switch (choice) {
            case 1:
                checkLoginStatus();
                controller.bookRoom(results, session);
                printBookingResultsMenu();
                performActionBookingResultsMenu(getMenuInput(1, 2));
                break;
            case 2:
                printUserRoomMenu();
                performActionUserRoomMenu(getMenuInput(1, 3));
                break;
            case 3:
                if (!session.isGuest()) {
                    printUserMainMenu();
                    performActionUserMainMenu(getMenuInput(1, 6));
                    break;
                } else break;
        }
    }

    private void performActionUserHotelMenu(int choice) {
        SearchResults results = null;
        switch (choice) {
            case 1:
                controller.findHotelByHotelName();
                if (!session.isGuest()) {
                    printUserMainMenu();
                    performActionUserMainMenu(getMenuInput(1, 6));
                    break;
                } else break;
            case 2:
                try {
                    results = controller.findHotelByCityDate();
                    if (results.getRooms().size() == 0) throw new NullSearchResultsException("");
                    if (results == null) throw new NullSearchResultsException("");
                } catch (NullSearchResultsException | NullPointerException e) {
                    System.out.println("There is no hotel matching your criteria");
                }
                if (results == null | results.getRooms().size() == 0) {
                    if (!session.isGuest()) {
                        printUserMainMenu();
                        performActionUserMainMenu(getMenuInput(1, 6));
                        break;
                    } else break;
                } else {
                    printUserHotelResultsMenu();
                    performActionUserHotelResultsMenu(results, getMenuInput(1, 3));
                    break;
                }
            case 3:
                if (!session.isGuest()) {
                    printUserMainMenu();
                    performActionUserMainMenu(getMenuInput(1, 6));
                    break;
                } else break;
        }
    }

    private void performActionUserHotelResultsMenu(SearchResults results, int choice) {
        switch (choice) {
            case 1:
                checkLoginStatus();
                controller.bookRoom(results, session);
                printBookingResultsMenu();
                performActionBookingResultsMenu(getMenuInput(1, 2));
                break;
            case 2:
                printUserHotelMenu();
                performActionUserHotelMenu(getMenuInput(1, 3));
                break;
            case 3:
                if (!session.isGuest()) {
                    printUserMainMenu();
                    performActionUserMainMenu(getMenuInput(1, 6));
                    break;
                } else break;
        }
    }

    /**
     * Prints out a Login Menu, prompting the unregistered user to login or register before making a booking.
     */
    private void printLoginMenu() {
        System.out.println("\nPlease make a selection");
        System.out.println("[1] Login");
        System.out.println("[2] Register");
    }

    private void performActionPrintLoginMenu(int choice) {
        switch (choice) {
            case 1:
                session = controller.login(session);
                break;
            case 2:
                session.setUser(controller.createUser());
                session.setGuest(false);
                session.setAdmin(false);
                break;
        }
    }

    private void printBookingResultsMenu() {
        System.out.println("\nPlease make a selection");
        System.out.println("[1] Go back to main menu");
        System.out.println("[2] Exit");
    }

    private void performActionBookingResultsMenu(int choice) {
        switch (choice) {
            case 1:
                printUserMainMenu();
                performActionUserMainMenu(getMenuInput(1, 6));
                break;
            case 2:
                System.out.println("Thank you for using our application, we hope to see you again soon!");
                controller.updateDB();
                exit = true;
                break;
        }
    }

    /**
     * Checks if current user is a guest and if so, prompts this user to login by printing out a Login Menu.
     *
     * @see #printLoginMenu()
     * @see #performActionPrintLoginMenu(int)
     */
    private void checkLoginStatus() {
        if (session.isGuest()) {
            System.out.println("You need to login first");
            while (session.isGuest()) {
                printLoginMenu();
                performActionPrintLoginMenu(getMenuInput(1, 2));
            }
        }
    }

    private void printUserBookingUpdateMenu() {
        System.out.println("Your user information:");
        System.out.println(User.getOutputHeader());
        System.out.println(session.getUser().getOutput());
        if (!controller.getUsersBookings(session.getUser()).isEmpty()) {
            System.out.println("Your bookings:");
            controller.printUserBookings(controller.createReservationMap(
                    controller.getUsersBookings(session.getUser())));
            System.out.println("Please make a selection:");
            System.out.println("[1] Update your profile"); // for admin / users
            System.out.println("[2] Change the dates of a reservation");
            System.out.println("[3] Delete a reservation");
            System.out.println("[4] Go back to main menu");
        } else {
            System.out.println("Please make a selection:");
            System.out.println("[1] Update your profile"); // for admin / users
            System.out.println("[2] Go back to main menu");
        }

    }

    private void performActionsUserBookingUpdateMenu(int choice) {

        if (!controller.getUsersBookings(session.getUser()).isEmpty()) {

            switch (choice) {
                case 1:
                    updateUser(session.getUser());
                    printUserMainMenu();
                    performActionUserMainMenu(getMenuInput(1, 6));
                    break;
                case 2:
                    System.out.println("logic to update booking");
                    controller.updateBooking(controller.createReservationMap(
                            controller.getUsersBookings(session.getUser())));
                    printUserMainMenu();
                    performActionUserMainMenu(getMenuInput(1, 6));
                    break;
                case 3:
                    controller.printUserBookings(controller.createReservationMap(
                            controller.getUsersBookings(session.getUser())));
                    if (controller.cancelRoomReservation(controller.chooseBookingFromList(controller.createReservationMap(
                            controller.getUsersBookings(session.getUser())))))
                        System.out.println("Your booking has been deleted successfully");
                    printUserMainMenu();
                    performActionUserMainMenu(getMenuInput(1, 6));
                    break;
                case 4:
                    printUserMainMenu();
                    performActionUserMainMenu(getMenuInput(1, 6));
                    break;
            }
        } else {
            switch (choice) {
                case 1:
                    updateUser(session.getUser());
                    printUserMainMenu();
                    performActionUserMainMenu(getMenuInput(1, 6));
                    break;
                case 2:
                    printUserMainMenu();
                    performActionUserMainMenu(getMenuInput(1, 6));
                    break;
            }
        }
    }


    // *********************************************************************************************
    // ************************************* ADMIN MENU ********************************************
    private boolean adminExit;

    /**
     * Run admin menu while <tt>adminExit</tt> is <tt>true</tt>.
     * @see #printAdminMainMenu()
     * @see #performActionAdminMainMenu(int choice)
     */
    private void adminMenu() {
        adminExit = false;
        while (!adminExit) {
            printAdminMainMenu();
            performActionAdminMainMenu(readIntToMaxNum(6));
        }
    }


//    *******************Admin verification*******************************************

    /**
     * Checks if login and password belongs to the admin.
     *
     * @param login    of the <tt>User</tt> whose belongs to admin is to be tested
     * @param password of the <tt>User</tt> whose belongs to admin is to be tested
     * @return <tt>true</tt> if login and password belongs to the admin
     * and false if <tt>User</tt> with the appropriate login and password doesn't exist or <tt>User</tt> isn't admin
     * @see #assignmentSessionForAdmin(User)
     * @see ProjectController#loginAndPasswordVerification(String, String)
     */
    private boolean adminLoginAndPasswordVerification(String login, String password) {

        User user = controller.loginAndPasswordVerification(login, password);
        if (user != null && user.getRole().equals(ADMIN)) {
            assignmentSessionForAdmin(user);
            return true;
        }
        return false;
    }

    /**
     * Checks whether the current session is an admin session or a registered user is admin.
     *
     * @return <tt>true</tt> if current session is an admin session or a registered user is admin
     * or false otherwise
     * @see #adminLoginAndPasswordVerification(String, String)
     * @see #performActionGuestMainMenu(int)
     * @see #performActionUserMainMenu(int)
     */
    private boolean isAdmin() {
        return session.isAdmin()
                || adminLoginAndPasswordVerification(readLogin(), readPassword());
    }

    /**
     * Sets the session to admin
     * @param admin is the <tt>User</tt> with admin rights
     * @see #adminLoginAndPasswordVerification(String, String)
     */
    private void assignmentSessionForAdmin(User admin) {
        session = new Session(admin);
    }


    //****************************** Admin main menu *********************************

    /**
     * Prints out a Admin Menu.
     * @see #adminMenu()
     */
    private void printAdminMainMenu() {
        System.out.println("");
        System.out.println("\tADMIN MENU");
        System.out.println("Please make a selection");
        System.out.println("[1] Add a hotel");
        System.out.println("[2] Add a room");
        System.out.println("[3] Update or delete a hotel");
        System.out.println("[4] Update or delete a room");
        System.out.println("[5] Update or delete a user");
        System.out.println("[6] Back to main menu");
    }

    /**
     * Processes the selection made by admin in Admin Menu,
     * calling the respective methods required to perform action selected by admin.
     * @param choice is number of the action selected by admin
     * @see #adminMenu()
     * @see #addHotel()
     * @see #addRoom()
     * @see #updateOrDeleteAHotel()
     * @see #updateOrDeleteARoom()
     * @see #updateOrDeleteUser()
     * @see #printUserMainMenu()
     * @see #performActionUserMainMenu(int)
     */
    private void performActionAdminMainMenu(int choice) {

        switch (choice) {
            case 1:
                addHotel();
                break;
            case 2:
                addRoom();
                break;
            case 3:
                updateOrDeleteAHotel();
                break;
            case 4:
                updateOrDeleteARoom();
                break;
            case 5:
                updateOrDeleteUser();
                break;
            case 6:
                adminExit = true;
                printUserMainMenu();
                performActionUserMainMenu(readIntToMaxNum(6));
                break;
            default://never happen
        }
    }


    //********************************Add hotel***************************************

    /**
     * Adds <tt>Hotel</tt> with settings received from the administrator
     * @see ConsoleWorkerUtil#readStringFromConsole()
     * @see ConsoleWorkerUtil#readPositiveInt()
     * @see Hotel
     * @see ProjectController#addHotel(Hotel)
     */
    private void addHotel() {

        System.out.println("Please enter the hotel name you want to add to database: ");
        String name = readStringFromConsole();

        //вынести в отдельную функцию считывание города и проверять его с Enum существующих городов
        System.out.println("Please enter the hotel city");
        String city = readStringFromConsole();

        System.out.println("Please enter the hotel rating");
        int rating = readPositiveInt();

        Hotel hotel = new Hotel(name, city, rating);

        try {
            controller.addHotel(hotel);
            System.out.println("Your hotel was created: " + hotel);
        } catch (HotelAlreadyExistsException e) {
            System.out.println(e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("HOTEL WASN'T CREATE!!!");
        }
    }


    //********************************Add room***************************************

    /**
     * Adds <tt>Room</tt> with settings received from the administrator
     * @see #performActionAdminMainMenu(int)
     * @see ConsoleWorkerUtil#readNameFromConsole(String)
     * @see ConsoleWorkerUtil#readIntToMaxNum(int)
     * @see ConsoleWorkerUtil#readPositiveInt()
     * @see #chooseHotelFromListOfHotelsWithSameHotelName(String)
     * @see #chooseRoomClass()
     * @see Room
     * @see ProjectController#createRoom(Room)
     */
    private void addRoom() {

        Hotel hotel;
        //System.out.println("Please enter the name of the hotel in which you want to add a room");
        String hotelName = readNameFromConsole(" the hotel in which you want to add a room");
        try {
            hotel = chooseHotelFromListOfHotelsWithSameHotelName(hotelName);
        } catch (NoOneHotelInDatabaseException e) {
            System.out.println(e.getMessage());
            return;// adminMenu();
        }

        System.out.println("Please enter the number of person:");
        int maxNumberOfPerson = 20;//можно сделать константу в Hotel - MAX_NUMBER_OF_GUESTS_IN_ROOM
        int numberOfPerson = readIntToMaxNum(maxNumberOfPerson);

        RoomClass roomClass = chooseRoomClass();

        System.out.println("Please enter the price per room:");
        int price = readPositiveInt();

        Room room = new Room(numberOfPerson, price, roomClass, hotel.getId());
        room.setHotelID(hotel.getId());//ПОКА НЕ ПРОВЕРЯЮ hotel НА null. hotel не должен быть равен null
        // т.к. в chooseHotelFromListOfHotelsWithSameHotelName это не допускается,
        // но при другой реализации может и быть равным null

        try {
            if (controller.createRoom(room) == null)
                throw new RuntimeException();
            System.out.println("Your room was successfully created: " + room);
            System.out.println(hotel);
        } catch (RoomAlreadyExistsException e) {
            System.out.println(e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("ROOM WASN'T CREATE!!!");
        }
    }

    /**
     * Chooses <tt>Hotel</tt> from list Of hotels with same hotel name <tt>hotelName</tt>
     * @param hotelName is the name of the hotel
     * @return <tt>Hotel</tt> selected by admin if hotel with <tt>hotelName</tt> exists
     * @throws NoOneHotelInDatabaseException if there isn't hotel with <tt>hotelName</tt> in database
     * @see #addRoom()
     * @see #updateOrDeleteAHotel()
     * @see #updateOrDeleteARoom()
     * @see ProjectController#findHotelByHotelName()
     * @see #createEntityMap(List)
     * @see Hotel#getOutputHeader()
     * @see Hotel#getOutput()
     * @see ConsoleWorkerUtil#readIntToMaxNum(int)
     */
    private Hotel chooseHotelFromListOfHotelsWithSameHotelName(String hotelName) throws NoOneHotelInDatabaseException {

        List<Hotel> listOfHotels = controller.findHotelByHotelName(hotelName);

        if (!listOfHotels.isEmpty()) {

            Map<Integer, Hotel> mapOfHotels = createEntityMap(listOfHotels);
            System.out.println("Please choose the number of the hotel you want to change: ");
            System.out.println("\t" + Hotel.getOutputHeader());
            mapOfHotels.forEach((key, value) -> System.out.println("[" + key + "]: " + value.getOutput()));

            int hotelKey = readIntToMaxNum(listOfHotels.size());

            return mapOfHotels.get(hotelKey);

        } else {
            throw new NoOneHotelInDatabaseException("There isn't hotel " + hotelName + " in database.");
        }
    }

    /**
     * Creats map of entities from list of entities with keys - number from 1
     * and values - elements of list entities
     * @param listOfEntities
     * @param <T> inherits <tt>Entity</tt>
     * @return map of entities from list of entities with keys - number from 1
     * and values - elements of list entities
     * @see #chooseHotelFromListOfHotelsWithSameHotelName(String)
     * @see #chooseRoomFromHotel(Hotel)
     */
    private <T extends Entity> Map<Integer, T> createEntityMap(List<T> listOfEntities) {

        Map<Integer, T> mapOfEntities = new HashMap<>(listOfEntities.size());

        int i = 0;
        for (T entity : listOfEntities) {
            i = i + 1;
            mapOfEntities.put(i, entity);
        }
        return mapOfEntities;
    }

    /**
     * Selects the class of room from a list provided by the class of room menu
     * @see #addRoom()
     * @see #updateRoom(Room)
     * @see #printClassOfRoomMenu()
     * @see ConsoleWorkerUtil#readIntToMaxNum(int)
     * @see RoomClass
     * @return RoomClass selected by admin
     */
    private RoomClass chooseRoomClass() {

        printClassOfRoomMenu();

        int classRoomNumber = readIntToMaxNum(5);

        switch (classRoomNumber) {
            case 1:
                return RoomClass.Standard;
            case 2:
                return RoomClass.Suite;
            case 3:
                return RoomClass.Business;
            case 4:
                return RoomClass.Apartment;
            case 5:
                return RoomClass.President;
            default:
                return RoomClass.Standard; // Never should happen
        }
    }

    /**
     * Prints out a Class of Room Menu.
     * @see #chooseRoomClass()
     */
    private void printClassOfRoomMenu() {
        System.out.println("Please choose a class of room: ");
        System.out.println("[1] Standard");
        System.out.println("[2] Suite");
        System.out.println("[3] Business");
        System.out.println("[4] Apartment");
        System.out.println("[5] President");
    }


//*********************** Update or delete a hotel *******************************

    /**
     * Updates or deletes the hotel by hotel name,
     * according to the choice made by the administrator.
     * @see #performActionAdminMainMenu(int)
     * @see ConsoleWorkerUtil#readStringFromConsole()
     * @see ConsoleWorkerUtil#readIntToMaxNum(int)
     * @see #chooseHotelFromListOfHotelsWithSameHotelName(String)
     * @see #printUpdateOrDeleteEntityMenu(String)
     * @see #updateHotel(Hotel)
     * @see #deleteHotel(Hotel)
     */
    private void updateOrDeleteAHotel() {

        System.out.println("Please enter the name of the hotel you want to update or delete");

        Hotel hotel;
        String hotelName = readStringFromConsole();
        try {
            hotel = chooseHotelFromListOfHotelsWithSameHotelName(hotelName);
        } catch (NoOneHotelInDatabaseException e) {
            System.out.println(e.getMessage());
            return;// adminMenu();
        }

        printUpdateOrDeleteEntityMenu("hotel");
        int numberOfSelectedAction = readIntToMaxNum(3);

        switch (numberOfSelectedAction) {
            case 1:
                updateHotel(hotel);
                break;
            case 2:
                deleteHotel(hotel);
                break;
            case 3:
                return; //adminMenu
        }
    }

    /**
     * Prints out a Update or Delete Entity Menu prompting the admin to update or delete entity.
     * @param entityName is name of entity
     * @see #updateOrDeleteAHotel()
     * @see #updateOrDeleteARoom()
     * @see #updateOrDeleteUser()
     */
    private void printUpdateOrDeleteEntityMenu(String entityName) {
        System.out.println("Please choose: ");
        System.out.println("[1] Update " + entityName);
        System.out.println("[2] Delete " + entityName);
        System.out.println("[3] Back to admin menu");
    }

    /**
     * Update <tt>Hotel</tt> with settings received from the administrator
     * @param hotel is Hotel to update
     * @see #updateOrDeleteAHotel()
     * @see Hotel
     * @see ConsoleWorkerUtil#readStringFromConsole()
     * @see ConsoleWorkerUtil#readIntFromConsole()
     * @see #createHotelParametersMap(String, String, Integer)
     * @see ProjectController#updateHotel(Hotel, Map)
     */
    private void updateHotel(Hotel hotel) {

        System.out.println("Do you want to change hotel " + HotelParameters.NAME + " ?");
        String newHotelName = null;
        if (confirm()) {
            System.out.println("Please enter new hotel " + HotelParameters.NAME);
            newHotelName = readStringFromConsole();
        }

        System.out.println("Do you want to change hotel " + CITY + " ?");
        String newHotelCity = null;
        if (confirm()) {
            System.out.println("Please enter new hotel " + CITY);
            newHotelCity = readStringFromConsole();
        }

        System.out.println("Do you want to change hotel " + RATING + " ?");
        Integer newHotelRating = null;
        if (confirm()) {
            System.out.println("Please enter new hotel " + RATING);
            newHotelRating = readIntFromConsole();
        }

        Map<HotelParameters, String> newParametersOfHotel =
                createHotelParametersMap(newHotelName, newHotelCity, newHotelRating);

        Hotel newHotel = controller.updateHotel(hotel, newParametersOfHotel);
        if (newHotel != null)
            System.out.println("Hotel was successfully updated: " + newHotel);
        else System.out.println("HOTEL WASN'T UPDATE!!!");
    }


    /**
     * Creates map of hotel parameters with keys: HotelParameters.NAME, HotelParameters.CITY,
     * HotelParameters.RATING and values: <tt>newHotelName</tt>, <tt>newHotelCity</tt>
     * and <tt>newHotelRating</tt>
     * @param newHotelName
     * @param newHotelCity
     * @param newHotelRating
     * @return map of hotel parameters with keys: HotelParameters.NAME, HotelParameters.CITY,
     * HotelParameters.RATING and values: <tt>newHotelName</tt>, <tt>newHotelCity</tt>
     * and <tt>newHotelRating</tt>
     * @see #updateHotel(Hotel)
     */
    private Map<HotelParameters, String> createHotelParametersMap
            (String newHotelName, String newHotelCity, Integer newHotelRating) {

        Map<HotelParameters, String> newParametersOfHotel = new HashMap<>();

        newParametersOfHotel.put(HotelParameters.NAME, newHotelName);
        newParametersOfHotel.put(CITY, newHotelCity);
        newParametersOfHotel.put(RATING, (newHotelRating == null) ? null : String.valueOf(newHotelRating));

        return newParametersOfHotel;
    }

    /**
     * Deletes the hotel if the administrator confirms the deletion
     * @param hotel for deletion
     * @see #updateOrDeleteAHotel()
     * @see ConsoleWorkerUtil#printConfirmDeleteEntity(String)
     * @see ProjectController#deleteHotel(Hotel)
     */
    private void deleteHotel(Hotel hotel) {

        printConfirmDeleteEntity("hotel");
        if (confirm() && controller.deleteHotel(hotel)) {
            System.out.println("Hotel was successfully deleted: " + hotel);
        } else
            System.out.println("HOTEL WASN'T DELETE!!!");
    }


//*********************** Update or delete a room *******************************

    /**
     * Updates or deletes the room,
     * according to the choice made by the administrator.
     * @see #performActionAdminMainMenu(int)
     * @see ConsoleWorkerUtil#readStringFromConsole()
     * @see ConsoleWorkerUtil#readIntToMaxNum(int)
     * @see #chooseHotelFromListOfHotelsWithSameHotelName(String)
     * @see #chooseRoomFromHotel(Hotel)
     * @see #printUpdateOrDeleteEntityMenu(String)
     * @see #updateRoom(Room)
     * @see #deleteRoom(Room)
     */
    private void updateOrDeleteARoom() {

        System.out.println("Please enter the name of the hotel of the room you want to update or delete");

        Hotel hotel;
        String hotelName = readStringFromConsole();
        try {
            hotel = chooseHotelFromListOfHotelsWithSameHotelName(hotelName);
        } catch (NoOneHotelInDatabaseException e) {
            System.out.println(e.getMessage());
            return;// adminMenu();
        }

        Room room;
        try {
            room = chooseRoomFromHotel(hotel);
        } catch (NoRoomsInHotelException e) {
            System.out.println(e.getMessage());
            return;// adminMenu();
        }

        printUpdateOrDeleteEntityMenu("room");
        int numberOfSelectedAction = readIntToMaxNum(3);

        switch (numberOfSelectedAction) {
            case 1:
                updateRoom(room);
                break;
            case 2:
                deleteRoom(room);
                break;
            case 3:
                return; //adminMenu
        }
    }

    /**
     * Chooses <tt>Room</tt> from list Of rooms in <tt>hotel</tt>
     * @param hotel is the hotel when we choose the room
     * @return <tt>Room</tt> selected by admin if rooms are exist in the hotel
     * @throws NoRoomsInHotelException if there aren't rooms in the hotel
     * @see #updateOrDeleteARoom()
     * @see #createEntityMap(List)
     * @see Room#getOutputHeader()
     * @see Room#getOutput()
     * @see ConsoleWorkerUtil#readIntToMaxNum(int)
     */
    private Room chooseRoomFromHotel(Hotel hotel) throws NoRoomsInHotelException {

        List<Room> listOfRooms = hotel.getRooms();

        if (listOfRooms != null) {

            Map<Integer, Room> mapOfRooms = createEntityMap(listOfRooms);

            System.out.println("Please choose the number of the room you want to change: ");
            System.out.println("\t" + Room.getOutputHeader());
            mapOfRooms.forEach((key, value) -> System.out.println("[" + key + "]: " + value.getOutput()));

            int roomKey = readIntToMaxNum(listOfRooms.size());

            return mapOfRooms.get(roomKey);

        } else {
            throw new NoRoomsInHotelException("There aren't rooms in " + hotel + ".");
        }
    }

    /**
     * Update <tt>Room</tt> with settings received from the administrator
     * @param room is Room to update
     * @see #updateOrDeleteARoom()
     * @see Room
     * @see ConsoleWorkerUtil#confirm()
     * @see ConsoleWorkerUtil#readIntFromConsole()
     * @see #chooseRoomClass()
     * @see #createRoomParametersMap(RoomClass, Integer, Integer)
     * @see ProjectController#updateRoom(Room, Map)
     */
    private void updateRoom(Room room) {

        System.out.println("Do you want to change room " + ROOM_CLASS + " ?");
        RoomClass newRoomClass = null;
        if (confirm()) {
            System.out.println("Please enter new " + ROOM_CLASS);
            newRoomClass = chooseRoomClass();
        }

        System.out.println("Do you want to change room " + CAPACITY + " ?");
        Integer newRoomCapacity = null;
        if (confirm()) {
            System.out.println("Please enter new room " + CAPACITY);
            newRoomCapacity = readIntFromConsole();
        }

        System.out.println("Do you want to change room " + PRICE + " ?");
        Integer newRoomPrice = null;
        if (confirm()) {
            System.out.println("Please enter new room " + PRICE);
            newRoomPrice = readIntFromConsole();
        }

        Map<RoomParameters, String> newRoomParametersMap =
                createRoomParametersMap(newRoomClass, newRoomCapacity, newRoomPrice);

        Room newRoom = controller.updateRoom(room, newRoomParametersMap);
        if (newRoom != null)
            System.out.println("Room was successfully updated: " + newRoom);
        else System.out.println("Room WASN'T UPDATE!!!");
    }

    /**
     * Creates map of room parameters with keys: ROOM_CLASS, CAPACITY,
     * PRICE and values: <tt>newRoomClass</tt>, <tt>newRoomCapacity</tt>
     * and <tt>newRoomPrice</tt>
     * @param newRoomClass
     * @param newRoomCapacity
     * @param newRoomPrice
     * @return map of room parameters with keys: ROOM_CLASS, CAPACITY,
     * PRICE and values: <tt>newRoomClass</tt>, <tt>newRoomCapacity</tt>
     * and <tt>newRoomPrice</tt>
     * @see #updateRoom(Room)
     */
    private Map<RoomParameters, String> createRoomParametersMap
            (RoomClass newRoomClass, Integer newRoomCapacity, Integer newRoomPrice) {

        Map<RoomParameters, String> newRoomParametersMap = new HashMap<>();

        newRoomParametersMap.put(ROOM_CLASS, (newRoomClass == null) ? null : newRoomClass.toString());
        newRoomParametersMap.put(CAPACITY, (newRoomCapacity == null) ? null : String.valueOf(newRoomCapacity));
        newRoomParametersMap.put(PRICE, (newRoomPrice == null) ? null : String.valueOf(newRoomPrice));

        return newRoomParametersMap;
    }


    /**
     * Deletes the room if the administrator confirms the deletion
     * @param room for deletion
     * @see #updateOrDeleteARoom()
     * @see ConsoleWorkerUtil#printConfirmDeleteEntity(String)
     * @see ProjectController#deleteRoom(Room)
     */
    private void deleteRoom(Room room) {

        printConfirmDeleteEntity("room");
        if (confirm() && controller.deleteRoom(room)) {
            System.out.println("Room was successfully deleted: " + room);
        } else
            System.out.println("ROOM WASN'T DELETE!!!");
    }


    //**************************** Update or delete user ***************************

    /**
     * Updates or deletes the user,
     * according to the choice made by the administrator.
     * @see #performActionAdminMainMenu(int)
     * @see ProjectController#findUserByLogin(String)
     * @see ConsoleWorkerUtil#readStringFromConsole()
     * @see ConsoleWorkerUtil#readIntToMaxNum(int)
     * @see #printUpdateOrDeleteEntityMenu(String)
     * @see #updateUser(User)
     * @see #deleteUser(User)
     */
    private void updateOrDeleteUser() {

        System.out.println("Please enter the username(login) of the user you want to update or delete");

        String userLogin = readStringFromConsole();
        User user = controller.findUserByLogin(userLogin);

        if (user != null) {

            if (user.getRole().equals(ADMIN)) {
                System.out.println("YOU CANNOT UPDATE OR DELETE ADMINS.");
                return;
            }

            printUpdateOrDeleteEntityMenu("user" + user);
            int numberOfSelectedAction = readIntToMaxNum(3);

            switch (numberOfSelectedAction) {
                case 1:
                    updateUser(user);
                    break;
                case 2:
                    deleteUser(user);
                    break;
                case 3:
                    return; //adminMenu
            }

        } else {
            System.out.println("There isn't user with " + userLogin + " login.");
        }
    }

    /**
     * Update <tt>User</tt> with settings received from the administrator
     * @param user is User to update
     * @see #updateOrDeleteUser()
     * @see User
     * @see ConsoleWorkerUtil#confirm()
     * @see ConsoleWorkerUtil#readStringFromConsole()
     * @see #createUserParametersMap(String, String, String)
     * @see ProjectController#updateUser(User)
     * @param user
     */
    private void updateUser(User user) {

        //дополнительная проверка на случай, если эту функции используют
        // еще где-нибудь кроме updateOrDeleteUser()
        if (user.getRole().equals(ADMIN)) {
            System.out.println("YOU CANNOT UPDATE ADMINS.");
            return;
        }

        System.out.println("Do you want to change user " + UserParameters.NAME + " ?");
        String newUserName = null;
        if (confirm()) {
            System.out.println("Please enter new user " + UserParameters.NAME);
            newUserName = readStringFromConsole();
        }

        System.out.println("Do you want to change user " + LOGIN + " ?");
        String newUserLogin = null;
        if (confirm()) {
            System.out.println("Please enter new user " + LOGIN);
            newUserLogin = readStringFromConsole();
        }

        System.out.println("Do you want to change user " + PASSWORD + " ?");
        String newUserPassword = null;
        if (confirm()) {
            System.out.println("Please enter new user " + PASSWORD);
            newUserPassword = readStringFromConsole();
        }

        Map<UserParameters, String> newParametersOfUser =
                createUserParametersMap(newUserName, newUserLogin, newUserPassword);

        User newUser = controller.updateUser(user, newParametersOfUser);
        if (newUser != null)
            System.out.println("User was successfully updated: " + newUser);
        else System.out.println("USER WASN'T UPDATE!!!");
    }

    /**
     * Creates map of user parameters with keys: UserParameters.NAME, LOGIN,
     * PASSWORD and values: <tt>newUserName</tt>, <tt>newUserLogin</tt>
     * and <tt>newUserPassword</tt>
     * @param newUserName
     * @param newUserLogin
     * @param newUserPassword
     * @return map of user parameters with keys: UserParameters.NAME, LOGIN,
     * PASSWORD and values: <tt>newUserName</tt>, <tt>newUserLogin</tt>
     * and <tt>newUserPassword</tt>
     */
    private Map<UserParameters, String> createUserParametersMap
            (String newUserName, String newUserLogin, String newUserPassword) {

        Map<UserParameters, String> newUserParameters = new HashMap<>();

        newUserParameters.put(UserParameters.NAME, newUserName);
        newUserParameters.put(LOGIN, newUserLogin);
        newUserParameters.put(PASSWORD, newUserPassword);

        return newUserParameters;
    }

    /**
     * Deletes the user if the administrator confirms the deletion
     * @param user for deletion
     * @see #updateOrDeleteUser()
     * @see ConsoleWorkerUtil#printConfirmDeleteEntity(String)
     * @see ProjectController#deleteUser(User)
     */
    private void deleteUser(User user) {

        //дополнительная проверка на случай, если эту функции используют
        // еще где-нибудь кроме updateOrDeleteUser()
        if (user.getRole().equals(ADMIN)) {
            System.out.println("YOU CANNOT DELETE ADMINS.");
            return;
        }

        printConfirmDeleteEntity("user" + user);

        if (confirm() && controller.deleteUser(user)) {
            System.out.println("User was successfully deleted: " + user);
        } else
            System.out.println("USER WASN'T DELETE!!!");
    }
}