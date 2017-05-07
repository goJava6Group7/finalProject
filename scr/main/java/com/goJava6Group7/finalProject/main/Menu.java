package com.goJava6Group7.finalProject.main;

import com.goJava6Group7.finalProject.controllers.ProjectController;
import com.goJava6Group7.finalProject.entities.*;
import com.goJava6Group7.finalProject.exceptions.frontend.*;

import java.util.*;

import static com.goJava6Group7.finalProject.entities.User.Role.*;
import static com.goJava6Group7.finalProject.utils.ConsoleWorkerUtil.*;
import static com.goJava6Group7.finalProject.utils.ConsoleWorkerUtil.HotelParameters.*;
import static com.goJava6Group7.finalProject.utils.ConsoleWorkerUtil.RoomParameters.*;
import static com.goJava6Group7.finalProject.utils.ConsoleWorkerUtil.UserParameters.*;

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
                performActionUserMainMenu(getMenuInput(1, 6));
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
                exit = true;
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
                    if (results == null) throw new NullSearchResultsException("");
                } catch (NullSearchResultsException | NullPointerException e) {
                    System.out.println("There is no room matching your criteria");
                }
                if (results == null) {
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
            case 2:
                try {
                    results = controller.findRoomByCityDate();
                    if (results == null) throw new NullSearchResultsException("");
                } catch (NullSearchResultsException | NullPointerException e) {
                    System.out.println("There is no room matching your criteria");
                }
                if (results == null) {
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
                } else break;
        }
    }

    private void performActionUserHotelMenu(int choice) {
        SearchResults results = null;
        switch (choice) {
            case 1:
                controller.findHotelByHotelName();
                break;
            case 2:
                try {
                    controller.findHotelByCityDate();
                    if (results == null) throw new NullSearchResultsException("");
                } catch (NullSearchResultsException | NullPointerException e) {
                    System.out.println("There is no hotel matching your criteria");
                }
                if (results == null) {
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
                } else break;
        }
    }

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
                System.exit(0);
        }
    }

    private void checkLoginStatus() {
        if (session.isGuest()) {
            System.out.println("You need to login first");
            while (session.isGuest()) {
                printLoginMenu();
                performActionPrintLoginMenu(getMenuInput(1, 2));
            }
        }
    }


    // ************************************* ADMIN MENU ********************************************
    private boolean adminExit;

    private void adminMenu() {
        adminExit = false;
        while (!adminExit) {
            printAdminMainMenu();
            performActionAdminMainMenu(readIntToMaxNum(7));
        }

//        TODO Вынести это туда, где будет вызываться админ меню
//        if (isAdmin()) {
//            adminMenu();
//        } else {
//            System.out.println("You do not have administrator rights. Login as administrator.");
//            printUserMainMenu();
//            performActionUserMainMenu(readIntToMaxNum(6));
//        }
    }


//    *******************Admin verification*******************************************

    /**
     * TODO Уточнить по проверке на админа!!! И о том, что должна возвращать функция loginAndPasswordVerification
     * TODO Использую для проверки роли user. Если user - администратор, то устанавливаю для него новую сессию
     * Не перенесла в controller, потому что надо вызывать assignmentSessionForAdmin(user) и работать с session
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
        //TODO ПРОВЕРКА НА АДМИНА ПРАВИЛЬНАЯ???
        if (user != null && user.getRole().equals(ADMIN)) {
            assignmentSessionForAdmin(user);
            return true;
        }
        return false;
    }

    private boolean isAdmin() {
        return session.isAdmin()
                || adminLoginAndPasswordVerification(readLogin(), readPassword());
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
    }


    //****************************** Admin main menu *********************************
    private void printAdminMainMenu() {
        System.out.println("");
        System.out.println("\tADMIN MENU");
        System.out.println("Please make a selection");
        System.out.println("[1] Choose database");
        System.out.println("[2] Add a hotel");
        System.out.println("[3] Add a room");
        System.out.println("[4] Update or delete a hotel");
        System.out.println("[5] Update or delete a room");
        System.out.println("[6] Update or delete a user");
        System.out.println("[7] Back to main menu");
    }

    private void performActionAdminMainMenu(int choice) {

        switch (choice) {
            case 1:
                changeTheDatabase();
                break;
            case 2:
                addHotel();
                break;
            case 3:
                addRoom();
                break;
            case 4:
                updateOrDeleteAHotel();
                break;
            case 5:
                updateOrDeleteARoom();
                break;
            case 6:
                updateOrDeleteUser();
                break;
            case 7:
                adminExit = true;
                printUserMainMenu();
                performActionUserMainMenu(readIntToMaxNum(6));
                break;
            default://never happen
        }

    }


    //********************************Choose database*********************************

    /**
     * TODO Достаточно просто сменить БД? Вся перезагрузка произойдет сама (в функциях backend)?
     * Kontar Maryna:
     * The method iInitializes the selected database
     */
    private void changeTheDatabase() {

        printAdminChooseDBMenu();

        int choiceDB = readIntToMaxNum(3);
        try {
            switch (choiceDB) {
                case 1:
                    printConfirmChangeDB();
                    if (confirm()) {
//                        getDataBaseManager(XML).initDB();
                        System.out.println("Now you work with XML database.");
                        System.exit(0);//Пока что просто выходим
                    } else {
                        System.out.println("DATABASE WAS NOT CHANGED.");
                        return;// adminMenu()
                    }
                case 2:
                    printConfirmChangeDB();
                    if (confirm()) {
//                        getDataBaseManager(BINARY).initDB();
                        System.out.println("Now you work with BINARY database.");
                        System.exit(0);//Пока что просто выходим
                    } else {
                        System.out.println("DATABASE WAS NOT CHANGED.");
                        return;// adminMenu()
                    }
                case 3:
                    return;//adminMenu()
            }
        } finally {
            //nothing here
        }/*catch (BackendException e) {
            e.printStackTrace();
            //TODO Подумать, что делать, если не поменяется база. Сообщение выдать (его пока нет в backend) и вернуться в админ меню?
//                    System.out.println(e.getMessage());
//                    return;// adminMenu()
        }*/
    }

    private void printAdminChooseDBMenu() {
        System.out.println("Please choose the database you want to work with");
        System.out.println("[1] XML database");
        System.out.println("[2] Binary database");
        System.out.println("[3] Back to admin menu");
    }


    //********************************Add hotel***************************************
    //TODO Определиться что считать одинаковыми отелями и где "ловить" одинаковый отель.
    // Здесь или в controller.addHotel(hotel). Если здесь, я могу перенаправить в adminMenu().
    // А если в controller.addHotel(hotel), то не понятно что делать с пойманым там исключением.
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

    //TODO Слишком сложный выбор отеля, надо подумать над упрощением
    private void addRoom() {

        Hotel hotel;
        System.out.println("Please enter the name of the hotel in which you want to add a room");
        String hotelName = readStringFromConsole();
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

        Room room = new Room(numberOfPerson, price, roomClass);
        room.setHotel(hotel);//ПОКА НЕ ПРОВЕРЯЮ hotel НА null.hotel не должен быть равен null
        // т.к. в chooseHotelFromListOfHotelsWithSameHotelName это не допускается, но при другой реализации может и быть равным null

        try {
            controller.createRoom(room);
            System.out.println("Your room was successfully created: " + room);
        } catch (RoomAlreadyExistsException e) {
            System.out.println(e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("ROOM WASN'T CREATE!!!");
        }
    }

    //TODO Слишком длинное имя у функциию. Возвращать null, если нет отелей или кидать exception
    private Hotel chooseHotelFromListOfHotelsWithSameHotelName(String hotelName) throws NoOneHotelInDatabaseException {

        List<Hotel> listOfHotels = controller.findHotelByHotelName(hotelName);

        if (listOfHotels != null) {

            Map<Integer, Hotel> mapOfHotels = createEntityMap(listOfHotels);
            System.out.println("Please choose the number of the hotel you want to change: ");
            System.out.println("\t" + Hotel.getOutputHeader());
            mapOfHotels.forEach((key, value) -> System.out.println("[" + key + "]: " + value.getOutput()));

            int hotelKey = readIntToMaxNum(listOfHotels.size());

            return mapOfHotels.get(hotelKey);

        } else {
            throw new NoOneHotelInDatabaseException("There isn't hotel " + hotelName + "  in database.");
        }
    }

    //TODO написала метод, чтобы можно было выберать номер отеля/комнаты из списка найденных отелей/комнат
    //Подумать как проше сделать
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
     * The method selects a class room from a list provided by the class of room menu
     *
     * @return RoomClass
     */
    private RoomClass chooseRoomClass() {

        printClassOfRoomMenu();

        int classRoomNumber = readIntToMaxNum(5);

        switch (classRoomNumber) {
            case 1:
                return RoomClass.Standart;
            case 2:
                return RoomClass.Suite;
            case 3:
                return RoomClass.Business;
            case 4:
                return RoomClass.Apartment;
            case 5:
                return RoomClass.President;
            default:
                return RoomClass.Standart; // Never should happen
        }
    }

    private void printClassOfRoomMenu() {
        System.out.println("Please choose a class of room: ");
        System.out.println("[1] Standard");
        System.out.println("[2] Suite");
        System.out.println("[3] Business");
        System.out.println("[4] Apartment");
        System.out.println("[5] President");
    }


//*********************** Update or delete a hotel *******************************

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

    private void printUpdateOrDeleteEntityMenu(String entityName) {
        System.out.println("Please choose: ");
        System.out.println("[1] Update " + entityName);
        System.out.println("[2] Delete " + entityName);
        System.out.println("[3] Back to admin menu");
    }

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

    private Map<HotelParameters, String> createHotelParametersMap
            (String newHotelName, String newHotelCity, Integer newHotelRating) {

        Map<HotelParameters, String> newParametersOfHotel = new HashMap<>();

        newParametersOfHotel.put(HotelParameters.NAME, newHotelName);
        newParametersOfHotel.put(CITY, newHotelCity);
        newParametersOfHotel.put(RATING, (newHotelRating == null) ? null : String.valueOf(newHotelRating));

        return newParametersOfHotel;
    }

    private void deleteHotel(Hotel hotel) {

        printConfirmDeleteEntity("hotel");
        if (confirm() && controller.deleteHotel(hotel)) {
            System.out.println("Hotel was successfully deleted: " + hotel);
        } else
            System.out.println("HOTEL WASN'T DELETE!!!");
    }


//*********************** Update or delete a room *******************************

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

    private Map<RoomParameters, String> createRoomParametersMap
            (RoomClass newRoomClass, Integer newRoomCapacity, Integer newHotelRating) {

        Map<RoomParameters, String> newRoomParametersMap = new HashMap<>();

        newRoomParametersMap.put(ROOM_CLASS, newRoomClass.toString());
        newRoomParametersMap.put(CAPACITY, (newRoomCapacity == null) ? null : String.valueOf(newRoomCapacity));
        newRoomParametersMap.put(PRICE, (newHotelRating == null) ? null : String.valueOf(newHotelRating));

        return newRoomParametersMap;
    }


    private void deleteRoom(Room room) {

        printConfirmDeleteEntity("room");
        if (confirm() && controller.deleteRoom(room)) {
            System.out.println("Room was successfully deleted: " + room);
        } else
            System.out.println("ROOM WASN'T DELETE!!!");
    }


    //**************************** Update or delete user ***************************

    private void updateOrDeleteUser() {

        System.out.println("Please enter the username(login) of the user you want to update or delete");

        String userLogin = readStringFromConsole();
        User user = controller.findUserByLogin(userLogin);

        if (user != null) {

            if(user.getRole().equals(ADMIN)){
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

    private void updateUser(User user) {

        //дополнительная проверка на случай, если єту функции используют
        // еще где-нибудь кроме updateOrDeleteUser()
        if (user.getRole().equals(ADMIN)){
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

    private Map<UserParameters, String> createUserParametersMap
            (String newUserName, String newUserLogin, String newUserPassword) {

        Map<UserParameters, String> newUserParameters = new HashMap<>();

        newUserParameters.put(UserParameters.NAME, newUserName);
        newUserParameters.put(LOGIN, newUserLogin);
        newUserParameters.put(PASSWORD, newUserPassword);

        return newUserParameters;
    }

    private void deleteUser(User user) {

        //дополнительная проверка на случай, если єту функции используют
        // еще где-нибудь кроме updateOrDeleteUser()
        if (user.getRole().equals(ADMIN)){
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