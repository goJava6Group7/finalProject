package com.goJava6Group7.finalProject.main;

import com.goJava6Group7.finalProject.controllers.ProjectController;
import com.goJava6Group7.finalProject.entities.*;
import com.goJava6Group7.finalProject.exceptions.frontend.HotelAlreadyExistsException;
import com.goJava6Group7.finalProject.exceptions.frontend.RoomAlreadyExistsException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.goJava6Group7.finalProject.utils.ConsoleWorkerUtil.*;

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
                performActionUserRoomResultsMenu(results, getMenuInput(1, 3));
                break;
            case 2:
                results = controller.findRoomByCityDate();
                printUserRoomResultsMenu();
                performActionUserRoomResultsMenu(results, getMenuInput(1, 3));
                break;
            case 3:
                if (!session.isGuest()){
                    printUserMainMenu();
                    performActionUserMainMenu(getMenuInput(1,6));
                } else break;
        }
    }

    private void performActionUserRoomResultsMenu(SearchResults results, int choice) {
        switch (choice) {
            case 1:
                checkLoginStatus();
                controller.bookRoom(results, session);
                printBookingResultsMenu();
                performActionBookingResultsMenu(getMenuInput(1,2));
                break;
            case 2:
                printUserRoomMenu();
                performActionUserRoomMenu(getMenuInput(1, 3));
                break;
            case 3:
                if (!session.isGuest()){
                    printUserMainMenu();
                    performActionUserMainMenu(getMenuInput(1,6));
                } else break;
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
                performActionUserHotelResultsMenu(results, getMenuInput(1, 3));
                break;
            case 3:
                if (!session.isGuest()){
                    printUserMainMenu();
                    performActionUserMainMenu(getMenuInput(1,6));
                } else break;
        }

    }

    private void performActionUserHotelResultsMenu(SearchResults results, int choice) {
        switch (choice) {
            case 1:
                checkLoginStatus();
                controller.bookRoom(results, session);
                printBookingResultsMenu();
                performActionBookingResultsMenu(getMenuInput(1,2));
                break;
            case 2:
                printUserHotelMenu();
                performActionUserHotelMenu(getMenuInput(1, 3));
                break;
            case 3:
                if (!session.isGuest()){
                    printUserMainMenu();
                    performActionUserMainMenu(getMenuInput(1,6));
                } else break;
        }
    }

    private void printLoginMenu() {
        System.out.println("\nPlease make a selection");
        System.out.println("[1] Login");
        System.out.println("[2] Register");
    }

    private void performActionPrintLoginMenu(int choice){
        switch(choice){
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

    private void performActionBookingResultsMenu(int choice){
        switch(choice){
            case 1:
                printUserMainMenu();
                performActionUserMainMenu(getMenuInput(1,6));
                break;
            case 2:
                System.out.println("Thank you for using our application, we hope to see you again soon!");
                System.exit (0);
        }
    }

    private void checkLoginStatus(){
        if (session.isGuest()){
            System.out.println("You need to login first");
            while(session.isGuest()){
                printLoginMenu();
                performActionPrintLoginMenu(getMenuInput(1, 2));
            }
        }
    }


// ************************************* ADMIN MENU ********************************************

    public void adminMenu() {
        printAdminMainMenu();
        performActionAdminMainMenu(getMenuInput(1, 7));

//        TODO Вынести это туда, где будет вызываться админ меню
//        if (isAdmin()) {
//            adminMenu();
//        } else {
//            System.out.println("You do not have administrator rights. Login as administrator.");
//            runMenu();
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
        if (user != null && user.getRole().equals(User.Role.ADMIN)) {
            assignmentSessionForAdmin(user);
            return true;
        }
        return false;
    }

    private boolean isAdmin() {
        return adminLoginAndPasswordVerification(readLogin(), readPassword());
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
        System.out.println("[2] Add a hotel");
        System.out.println("[3] Add a room");
        System.out.println("[4] Update or delete a hotel"); // do like we do for users
        System.out.println("[5] Update or delete a room");
        System.out.println("[6] Find and update a user"); // search user by userName
        System.out.println("[7] Back to main menu");
    }

    private void performActionAdminMainMenu(int choice) {

        switch (choice) {
            case 1:
                chooseTheDatabase();
            case 2:
                addHotel();
            case 3:
                addRoom();
            case 4: //TODO
            case 5:
            case 6:
            case 7: runMenu();
            default:
        }

    }

    //********************************Choose database*********************************

    /**
     * TODO Достаточно просто сменить БД? Вся перезагрузка произойдет сама (в функциях backend)?
     * Kontar Maryna:
     * The method iInitializes the selected database
     */
    private void chooseTheDatabase() {

        adminChooseDBMenu();

        int choiceDB = getMenuInput(1, 3);
        try {
            switch (choiceDB) {
                case 1:
                    printConfirmChangeDB();
                    if (confirm()) {
//                        getDataBaseManager(XML).initDB();
                        System.exit(0);//Пока что просто выходим
                    } else adminMenu();

                case 2:
                    printConfirmChangeDB();
                    if (confirm()) {
//                        getDataBaseManager(BINARY).initDB();
                        System.exit(0);//Пока что просто выходим
                    } else adminMenu();

                case 3:
                    adminMenu();
            }
        } finally {
            //nothing here
        }/*catch (BackendException e) {
            e.printStackTrace();
            //TODO Подумать, что делать, если не поменяется база. Сообщение выдать (его пока нет в backend) и вернуться в админ меню?
//                    System.out.println(e.getMessage());
//                    adminMenu();
        }*/
    }

    public void adminChooseDBMenu() {
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

        System.out.println("Please enter the hotel name you want to add to database: "
                + controller.dbManager.getClass().getSimpleName());
        String name = readStringFromConsole();

        //вынести в отдельную функцию считывание города и проверять его с Enum существующих городов
        System.out.println("Please enter the hotel city");
        String city = readStringFromConsole();

        System.out.println("Please enter the hotel кфештп");
        int rating = readPositiveInt();


        Hotel hotel = new Hotel(name, city, rating);

        try {
            controller.addHotel(hotel);
        } catch (HotelAlreadyExistsException e) {
            System.out.println(e.getMessage());
            adminMenu();
        }

        System.out.println("Your hotel was created: " + hotel);

        adminMenu();
    }

    public void addRoom() {

        System.out.println("Please enter the name of the hotel in which you want to add a room");
        String hotelName = readStringFromConsole();
        List<Hotel> listOfHotels = controller.findHotelByHotelName(hotelName);

        if (!listOfHotels.isEmpty()) {

            Map<Integer, Hotel> mapOfHotels = createHotelMap(listOfHotels);
            System.out.println("Please choose the number of the hotel " +
                    "in which you want to add a room:");
            mapOfHotels.forEach((key, value) -> System.out.println("[" + (key + 1) + "]: " + value));

            int hotelKey = readIntToMaxNum(listOfHotels.size());
            Hotel hotel = mapOfHotels.get(hotelKey);

            System.out.println("Please enter the number of person:");
            int maxNumberOfPerson = 10;//можно сделать константу в Hotel - MAX_NUMBER_OF_GUESTS_IN_ROOM
            int numberOfPerson = readIntToMaxNum(maxNumberOfPerson);

            //TODO(Игорь) добавлен Enum для классов комнат. Нужно реализовать их выбор
//            System.out.println("Please enter a class of room:");
            RoomClass roomClass = RoomClass.Apartment;//нужен Enum для классов комнат

            System.out.println("Please enter the price per room:");
            int price = readPositiveInt();

            Room room = new Room(numberOfPerson, price, roomClass);
            room.setHotel(hotel);
            //TODO убрала проверку на null, т.к. все параметры проверяю при вводе

            //TODO когда у backend появится поле Hotel в Room переделать метод в контроллере для добавления комнаты
            try {
                controller.createRoom(room);
            } catch (RoomAlreadyExistsException e) {
                System.out.println(e.getMessage());
                adminMenu();
            }

            System.out.println("Your room was successfully created: " + room);

            adminMenu();

        } else {
            System.out.println("There isn't hotel " + hotelName + " in database. Try again");
            adminMenu();// or addRoom()
        }
    }

    //TODO написала метод, чтобы можно было выберать номер отеля из списка найденных по имени отелей
    //Подумать как проше сделать
    private Map<Integer, Hotel> createHotelMap(List<Hotel> listOfHotels) {
        List<Integer> listOfHotelNumbers = Arrays.asList(new Integer[listOfHotels.size()]);
        Map<Integer, Hotel> mapOfHotels = new HashMap<>();
        mapOfHotels.values().addAll(listOfHotels);
        mapOfHotels.keySet().addAll(listOfHotelNumbers);
        return mapOfHotels;
    }




    private void searchUser() {
        System.out.println("Please enter the login of the user");
        //search user method; not void but User. User search not void, but string; and admin search is Hotel.
        // two points: update user, delete user. new menu with 3 points: update user, delete user, go back to admin menu
        // do not update / delete admins: so in search, if it is admin, then return: "you cannot update or delete admins"

    }


}