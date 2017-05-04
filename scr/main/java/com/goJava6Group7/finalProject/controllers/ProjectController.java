package com.goJava6Group7.finalProject.controllers;

import com.goJava6Group7.finalProject.data.dao.Dao;
import com.goJava6Group7.finalProject.data.dataBase.DataBaseManager;
import com.goJava6Group7.finalProject.entities.*;
import com.goJava6Group7.finalProject.exceptions.frontend.*;
import com.goJava6Group7.finalProject.main.Session;
import com.goJava6Group7.finalProject.utils.ConsoleWorkerUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.goJava6Group7.finalProject.entities.User.Role.ADMIN;
import static com.goJava6Group7.finalProject.utils.ConsoleWorkerUtil.*;


/**
 * Created by Igor on 13.04.2017.
 */
public class ProjectController {

    public DataBaseManager dbManager;

    public ProjectController(DataBaseManager dbManager) {
        this.dbManager = dbManager;
    }

    // ************************* MARINA ********************************************************

    /**
     * TODO что должна возвращать функция loginAndPasswordVerification???
     * TODO Мне нужен User, чтобы использовать в функции adminLoginAndPasswordVerification(String login, String password)
     * Kontar Maryna:
     * The method checks the presence of the user with the appropriate login and password
     *
     * @param login
     * @param password
     * @return User if user with the appropriate login and password exist
     * and null otherwise
     */
    public User loginAndPasswordVerification(String login, String password) {
        List<User> allUsers = dbManager.getDaoUser().getAll();
        Optional<User> optional = allUsers.stream()
                .filter(o -> o.getName().equals(login) && o.getLogin().equals(password))
                .findFirst();
        return optional.orElse(null);
    }


    /**
     * TODO Игорю на проверку
     * TODO Эта функция написана мной, п.ч. изначально так поделили задания с Гийомом,
     * TODO но потом мне надо было реализовывать админ меню, а функции остались
     * Kontar Maryna:
     * Method create account for user with name, login and password
     * if there isn't account with this name and login.
     *
     * @param name
     * @param login
     * @param password
     * @return User if an account is created
     * @throws AccountAlreadyExistException
     */
    public User createAccount(String name, String login, String password) throws AccountAlreadyExistException {

        Dao<User> daoUser = dbManager.getDaoUser();
        List<User> allUsers = daoUser.getAll();
        User user = new User(name, login, password);
        if (allUsers.stream()
                .anyMatch((User o) -> o.getName().equals(name) || o.getLogin().equals(login))) {
            throw new AccountAlreadyExistException("Account with these name or login already exists.");
        }
        return daoUser.create(user);
    }

    /**
     * TODO Эта функция написана мной, п.ч. изначально так поделили задания с Гийомом,
     * TODO но потом мне надо было реализовывать админ меню, а функции остались
     * TODO Возможно надо написать функцию isFreeRoom(){return !isBooked}.
     * TODO потому что отрицанию воспринимаются мозгом намного медленнее
     * Kontar Maryna:
     *
     * @param reserveOnUser
     * @param room
     * @param dataOfArrival
     * @param dateOfDeparture
     * @return Reservation if reservation is created
     * @throws RoomIsReservedForTheseDatesException
     */
    public Reservation reserveRoom(User reserveOnUser, Room room, LocalDate dataOfArrival, LocalDate dateOfDeparture)
            throws RoomIsReservedForTheseDatesException {
        Dao<Reservation> daoReservation = dbManager.getDaoReservation();
        if (room.getHotel()
                .getHotelRooms()
                .stream()
                .noneMatch(roomAtHotel -> !isBooked(roomAtHotel, dataOfArrival, dateOfDeparture))) {
            throw new RoomIsReservedForTheseDatesException("The room is reserved for these dates: "
                    + dataOfArrival + " - " + dateOfDeparture);
        }
        return daoReservation.create(new Reservation(reserveOnUser, room, dataOfArrival, dateOfDeparture));

        //TODO Функция должна быть с входными параметрами.
        //TODO Метод create сохранит этот reservation в БД и добавит в список бронирований данного user?
        // Спросила у ребят из backend. Жду, пока они дойдут до этого
    }

    /**
     * TODO Эта функция написана мной, п.ч. изначально так поделили задания с Гийомом,
     * TODO но потом мне надо было реализовывать админ меню, а функции остались
     * Kontar Maryna:
     * The method delete room reservation
     *
     * @param reservation
     * @return true if the deletion was successful and false otherwise
     */
    public boolean cancelRoomReservation(Reservation reservation) {

        //TODO Проверять на наличие в БД НЕ НАДО (это сделано backend в функции delete(Reservation reservation) в DaoReservation)
        Dao<Reservation> daoReservation = dbManager.getDaoReservation();
        return daoReservation.delete(reservation);
    }


    /**
     * TODO Игорю на проверку
     * TODO(Замечания) - определится с тем, что будет считаться идентичным Отелем
     * Kontar Maryna:
     * The method adds the hotel to the database, if the hotel is not in the database
     *
     * @param hotel
     * @return the added hotel
     * @throws HotelAlreadyExistsException
     */
    public Hotel addHotel(Hotel hotel) throws HotelAlreadyExistsException {

        Dao<Hotel> daoHotel = dbManager.getDaoHotel();
        List<Hotel> allHotels = daoHotel.getAll();

        //TODO Надо проверять на наличие в БД, т.к. create(hotel) в DaoHotel не проверяет
        if (allHotels.stream().anyMatch(hotelAtDatabase -> hotelAtDatabase.equals(hotel))) {
            throw new HotelAlreadyExistsException("The " + hotel + "already exists in database "
                    + dbManager.getClass().getSimpleName());
        }
        return daoHotel.create(hotel);
    }

    /**
     * TODO Игорю на проверку
     * Kontar Maryna:
     * The method delete hotel and return true if the deletion was successful
     *
     * @param hotel
     * @return true if the deletion was successful and false otherwise
     */
    public boolean deleteHotel(Hotel hotel) {
        //TODO Проверять на наличие в БД НЕ НАДО (это сделано backend в функции delete(Hotel hotel) в DaoHotel)
        Dao<Hotel> daoHotel = dbManager.getDaoHotel();
        return daoHotel.delete(hotel);
    }

//    public boolean deleteHotel(Hotel hotel) throws HotelIsNotInDatabaseException {
//
//        if(allHotels.stream().anyMatch(hotelAtDatabase -> hotelAtDatabase.equals(hotel))){
//            throw new HotelIsNotInDatabaseException("The " + hotel + "is not in database "
//                    + dbManager.getClass().getSimpleName());
//        }
//        return DAOHotel.delete(hotel);
//    }

    /**
     * TODO СТАРОЕ Игорю на проверку НАВЕРНОЕ НАДО ПОМЕНЯТЬ СИГНАТУРУ МЕТОДА update на update(Hotel hotel, Hotel newHotel)
     * TODO СТАРОЕ (Замечания) - согласен
     * TODO у backend метод update заменяет отель, если "hotels.stream().filter(i -> i.equals(hotel))".
     * TODO А equals у них по всем 4-ом параметрам. НАДО ОБСУДИТЬ!!!!
     * (или на update(Hotel hotel, параметры отеля))
     * Kontar Maryna:
     *
     * @param hotel
     * @param newHotel
     * @return
     * @throws HotelIsNotInDatabaseException
     */
    public Hotel updateHotel(Hotel hotel, Hotel newHotel) throws HotelIsNotInDatabaseException {
//        Dao<Hotel> daoHotel = dbManager.getDaoHotel();
        //TODO(Замечания) - лишняя проверка. Если мы будем делать update, то к этому моменту уже будем знать, что отель существует
//        if (allHotels.stream().anyMatch(currentHotel -> hotel.equals(hotel))) {
//            throw new HotelIsNotInDatabaseException("The " + hotel + "is not in database "
//                    + dbManager.getClass().getSimpleName());
//        }
        return dbManager.getDaoHotel().update(newHotel);
    }

    /**
     * TODO Игорю на проверку. У backend в Room нет поля Hotel.
     * TODO Поэтому сначала создаю абстрактную комнату(createRoom)а потом добавляю ее к отелю (addRoomToHotel).
     * TODO Видимо придется переделывать эти две функции в одну и не проверять есть ли комната в отеле,
     * TODO т.к. могут быть одинаковые комнаты. Или проверять только по id
     * Kontar Maryna:
     * The method create room if room isn't in database
     *
     * @param room
     * @return created room
     * @throws RoomAlreadyExistsException
     */
    public Room createRoom(Room room) throws RoomAlreadyExistsException {

        Dao<Room> daoRoom = dbManager.getDaoRoom();
        List<Room> allRooms = daoRoom.getAll();

        if (allRooms.stream().anyMatch(roomAtDatabase -> roomAtDatabase.equals(room))) {
            throw new RoomAlreadyExistsException("The " + room + "already exists in database "
                    + dbManager.getClass().getSimpleName());
        }
        return daoRoom.create(room);
    }

    /**
     * TODO Решить, что будет возвращать метод (boolean, Room). Надо ли проверять существует ли комната в БД и дописать javaDoc
     * Kontar Maryna:
     * The method add room to the hotel
     *
     * @param room
     * @param hotel
     */
    public void addRoomToHotel(Room room, Hotel hotel) {

        hotel.getHotelRooms().add(room);
    }

    /**
     * TODO Игорю на проверку
     * Kontar Maryna:
     * The method delete room from hotel and return true if the deletion was successful
     *
     * @param room
     * @return true if the deletion of room was successful and false otherwise
     */
    public boolean deleteRoom(Room room) {
        //TODO Проверять на наличие в БД НЕ НАДО (это сделано backend в функции delete(Room room) в DaoRoom)
        Dao<Room> daoRoom = dbManager.getDaoRoom();
        return daoRoom.delete(room);
    }

    //TODO Не готово. Надо обсудить и подумать
        public Room updateRoom(Room oldRoom, HashMap<String, String> newParametersOfRoom) {
//        newParametersOfRoom.keySet().stream().map(parameterName ->);

        oldRoom.setPrice(Integer.parseInt(newParametersOfRoom.get("Price")));
        oldRoom.setName(newParametersOfRoom.get("Name"));
        throw new UnsupportedOperationException();
    }

    /**
     * TODO Игорю на проверку
     * Kontar Maryna:
     * The method delete User
     * @param user
     * @return true if the deletion was successful and false otherwise
     */
    public boolean deleteUser(User user){
        Dao<User> daoUser = dbManager.getDaoUser();
        return daoUser.delete(user);
    }

    
// ************************************* GUILLAUME ********************************************

    public void findHotelByHotelName() {

        Dao<Hotel> daoHotel = dbManager.getDaoHotel();
        List<Hotel> allHotels = daoHotel.getAll();

        String hotelName = ConsoleWorkerUtil.readNameFromConsole("hotel name");

        System.out.println("Here is a list of hotels matching your criteria: ");

        List<Hotel> myHotels = allHotels.stream()
                .filter((Hotel hotel) -> hotel.getHotelName().equalsIgnoreCase(hotelName))
                .collect(Collectors.toList());

        System.out.println(myHotels);

        System.out.println("To book a room, please choose the 'book a room' or 'search hotel by" +
                "city and dates' options in the main menu");

    }

    public SearchResults findHotelByCityDate() {

        String cityName = ConsoleWorkerUtil.readNameFromConsole("city name");
        LocalDate checkin = ConsoleWorkerUtil.getCheckinDate();
        LocalDate checkout = ConsoleWorkerUtil.getCheckoutDate(checkin);

        List<Room> rooms = searchRoomByCityDate(cityName, checkin, checkout);

        // create array of hotels with available rooms from the room array
        List<Hotel> hotelDuplicates = new ArrayList<>();
        for (Room room : rooms) {
            hotelDuplicates.add(room.getHotel());
        }

        // removing duplicates
        Set<Hotel> hotelsNoD = new HashSet<Hotel>();
        hotelsNoD.addAll(hotelDuplicates);

        List<Hotel> hotelsByCityByDate = new ArrayList<>();
        hotelsByCityByDate.addAll(hotelsNoD);

        // here it would be good to print the hotels without the rooms, do this later
        System.out.println("\nHere is a list of hotels with rooms available when you will be in " + cityName +
                " from " + checkin + " to " + checkout);


        // printing results in a clean way, showing only available rooms
        // i is used to as a reference number for booking function, in case they want to book a room
        final int[] i = {0};
        hotelsByCityByDate.forEach(hotel->{
            System.out.println("\n" + hotel.getHotelName() + ":");
            rooms.forEach(room->{
                if ((room.getHotel().getHotelName()).equalsIgnoreCase(hotel.getHotelName()))
                    System.out.println("   "+ i[0] + ": Room name: " + room.getName() +"; # of guests: " +
                            room.getNumberOfPersons() + "; Price per night: " + room.getPrice() + ".");
                i[0]++;
            });
        });

        SearchResults results = new SearchResults(checkin, checkout, rooms);

        return results;

    }

    public static boolean isBooked(Room room, LocalDate checkin, LocalDate checkout) {
        boolean isBooked = false;
        List<Reservation> bookings;

        bookings = room.getBookings();
        if (bookings == null) {
            isBooked = false;
        } else {
            for (Reservation booking : bookings) {
                // if checkin or checkout dates are during an existing stay
                if ((booking.getDateOfArrival().isBefore(checkin) &&
                        (booking.getDateOfDeparture().isAfter(checkin)))
                        || (booking.getDateOfArrival().isBefore(checkout) &&
                        (booking.getDateOfDeparture().isAfter(checkout)))) isBooked = true;

                // if checkin or checkout dates are the same as an existing stay
                if ((booking.getDateOfArrival().isEqual(checkin))
                        || (booking.getDateOfArrival().isEqual(checkout))) isBooked = true;

                // if checkin before and checkout after checkin and checkout of existing stay
                if ((checkin.isBefore(booking.getDateOfArrival()))
                        && (checkout.isAfter(booking.getDateOfArrival()))) isBooked = true;
            }
        }

        return isBooked;
    }

    public SearchResults findRoomByCityDate() {
        List<Room> rooms;

        String cityName = readNameFromConsole("city name");
        LocalDate checkin = getCheckinDate();
        LocalDate checkout = getCheckoutDate(checkin);

        rooms = searchRoomByCityDate(cityName, checkin, checkout);

        System.out.println("Here are the rooms available in " + cityName +
                " from " + checkin + " to " + checkout);
        System.out.println(rooms);

        SearchResults results = new SearchResults(checkin, checkout, rooms);

        return results;
    }

    public List<Room> searchRoomByCityDate(String cityName, LocalDate checkin, LocalDate checkout) {

        Dao<Hotel> daoHotel = dbManager.getDaoHotel();
        List<Hotel> allHotels = daoHotel.getAll();
        List<Room> rooms = new ArrayList<>();

        List<Hotel> cityHotels = allHotels.stream()
                .filter((Hotel hotel) -> hotel.getHotelCity().equalsIgnoreCase(cityName))
                .collect(Collectors.toList());

        // create room array with all rooms in the city
        for (Hotel hotel : cityHotels) {
            rooms.addAll(hotel.getHotelRooms());
        }

        // delete room if it is booked during requested period
        rooms.removeIf(room -> isBooked(room, checkin, checkout));

        return rooms;
    }


    public SearchResults findRoomByHotelDate() {

        Dao<Hotel> daoHotel = dbManager.getDaoHotel();
        List<Hotel> allHotels = daoHotel.getAll();

        List<Hotel> myHotels;
        List<Room> rooms = new ArrayList<>();

        String hotelName = readNameFromConsole("hotel name");
        LocalDate checkin = getCheckinDate();
        LocalDate checkout = getCheckoutDate(checkin);

        myHotels = allHotels.stream()
                .filter((Hotel hotel) -> hotel.getHotelName().equalsIgnoreCase(hotelName))
                .collect(Collectors.toList());

        // create room array with all rooms in the city
        for (Hotel hotel : myHotels) {
            rooms.addAll(hotel.getHotelRooms());
        }

        // delete room if it is booked during requested period
        rooms.removeIf(room -> isBooked(room, checkin, checkout));

        System.out.println("Here are the rooms available in the hotel " + hotelName +
                " from " + checkin + " to " + checkout);

        System.out.println(rooms);

        SearchResults results = new SearchResults(checkin, checkout, rooms);

        return results;

    }


    public void bookRoom(SearchResults results) {

        LocalDate checkin = results.getCheckin();
        LocalDate checkout = results.getCheckout();
        List<Room> rooms = results.getRooms();

        System.out.println("Please enter the number of the room you would like to book" +
                " from " + checkin + " to " + checkout);
        System.out.println(rooms);

    }

    public List<Room> findRoomsInHotel(Hotel hotel) {

        return hotel.getHotelRooms();

    }

    public Session login (Session session){
        String userName;
        String pass;
        User user;

        if (!session.isGuest()) System.out.println("You already logged in as " + session.getUser().getLogin());

        if (session.isGuest()){
            // get login credentials from user
            userName = readNameFromConsole("your username");
            pass = readNameFromConsole("your password");

            // try to login
            user = loginAndPasswordVerification(userName, pass);
            if (user == null) System.out.println("Wrong login credentials: please either register or try again");
            else{
                // update session info
                session.setUser(user);
                session.setGuest(false);

                if (user.getRole()==ADMIN) session.setAdmin(true);
            }
        }

        return session;
    }


    public Session logout(Session session){
        session.setUser(null);
        session.setGuest(true);
        session.setAdmin(false);

        return session;
    }

    public User createUser() {

        User newUser;
        String userName = "";
        String name = "";
        String password = "";
        String yn;

        boolean ok = false;

        while (!ok) {
            name = readNameFromConsole("your name");
            userName = readNameFromConsole("your username");
            password = readNameFromConsole("your password");

            System.out.println("\nHere is a summary of your data:");
            System.out.println("Name: " + name);
            System.out.println("Username: " + userName);

            System.out.println("If this is correct, please enter Y, else press any key");

            while (true) {
                try {
                    yn = readStringFromConsole();
                    break;
                } catch (IOException e) {
                    System.out.println("You entered a wrong input, please try again");
                    continue;
                }
            }
            if (yn.equalsIgnoreCase("Y")) ok = true;
        }

        newUser = new User(name, userName, password);

        // procedure to save user to DB
        Dao<User> daoUser = dbManager.getDaoUser();

        // check if user exists, if not create new user in DAO
        List<User> allUsers = dbManager.getDaoUser().getAll();
        String finalName = name;
        String finalUserName = userName;
        if (allUsers.stream()
                .anyMatch((User o) -> o.getName().equalsIgnoreCase(finalName) || o.getLogin().equals(finalUserName)))
            System.out.println("An account with this name and / or login already exists. Please try again");

        else daoUser.create(newUser);

        return newUser;

    }

    public User updateUser(User user){

        String userName;
        String pass;
        String name;

        // get new data from user
        System.out.println("Please enter your updated information.");
        name = readNameFromConsole("name");
        userName = readNameFromConsole("new username");
        pass = readNameFromConsole("new password");

        user.setName(name);
        user.setLogin(userName);
        user.setPassword(pass);

        // procedure to save user to DB
        Dao<User> daoUser = dbManager.getDaoUser();
        daoUser.update(user);

        System.out.println("Your data has been successfully saved");

        return user;
    }


}
