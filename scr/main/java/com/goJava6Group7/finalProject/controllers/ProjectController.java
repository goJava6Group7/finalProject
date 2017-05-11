package com.goJava6Group7.finalProject.controllers;

import com.goJava6Group7.finalProject.data.dao.Dao;
import com.goJava6Group7.finalProject.data.dataBase.DataBaseManager;
import com.goJava6Group7.finalProject.entities.*;
import com.goJava6Group7.finalProject.exceptions.frontend.*;
import com.goJava6Group7.finalProject.main.Session;
import com.goJava6Group7.finalProject.utils.ConsoleWorkerUtil;
import com.goJava6Group7.finalProject.entities.Room.RoomParameters;
import com.goJava6Group7.finalProject.entities.Hotel.HotelParameters;
import com.goJava6Group7.finalProject.entities.User.UserParameters;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.goJava6Group7.finalProject.entities.User.Role.ADMIN;
import static com.goJava6Group7.finalProject.utils.ConsoleWorkerUtil.*;


/**
 * Created by Igor on 13.04.2017.
 */
public class ProjectController {

    private DataBaseManager dbManager;

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
                .filter(o -> o.getLogin().equals(login) && o.getPassword().equals(password))
                .findFirst();
        return optional.orElse(null);
    }

    /**
     * TODO Добавлять ли отель с уже существующим именем, названием города и рейтингом?
     * У него будет другое id и если equals по id, то это разные отели
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
        //Т.к. теперь equals по id, то проверка видимо нужна по другим параметрам
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

        Dao<Hotel> daoHotel = dbManager.getDaoHotel();
        return daoHotel.delete(hotel);
    }

    /**
     * Kontar Maryna:
     *
     * @param hotel
     * @param newParametersOfHotel
     * @return
     */
    public Hotel updateHotel(Hotel hotel, Map<HotelParameters, String> newParametersOfHotel) {

        Dao<Hotel> daoHotel = dbManager.getDaoHotel();

        for (Map.Entry<HotelParameters, String> entry : newParametersOfHotel.entrySet()) {
            String value = entry.getValue();
            switch (entry.getKey()) {
                case NAME:
                    if (value != null)
                        hotel.setName(value);
                    break;
                case CITY:
                    if (value != null)
                        hotel.setCity(value);
                    break;
                case RATING:
                    if (value != null)
                        hotel.setRating(Integer.parseInt(value));
                    break;
            }
        }
        return daoHotel.update(hotel);
    }

    /**
     * TODO проверять есть ли комната в отеле? Могут быть одинаковые по всем параметрам (кроме id) комнаты в отеле.
     * TODO Или проверять только по id? Или equals только по id
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

        if (allRooms.stream()
                .anyMatch(roomAtDatabase -> roomAtDatabase.equals(room))
//                .anyMatch(roomAtDatabase -> roomAtDatabase.getId() == room.getId())
                )
            throw new RoomAlreadyExistsException("The " + room + "already exists in database "
                    + dbManager.getClass().getSimpleName());
        Hotel hotel = getHotelFromID(room.getHotelID());
        if (dbManager.getDaoHotel().addRoomToHotel(hotel, room) == null) return null;

        return daoRoom.create(room);
    }

    public Room updateRoom(Room room, Map<RoomParameters, String> newParametersOfRoom) {

        Dao<Room> daoRoom = dbManager.getDaoRoom();

        for (Map.Entry<RoomParameters, String> entry : newParametersOfRoom.entrySet()) {
            String value = entry.getValue();
            switch (entry.getKey()) {
                case ROOM_CLASS:
                    if (value != null)
                        room.setRoomClass(RoomClass.valueOf(entry.getValue()));
                    break;
                case CAPACITY:
                    if (value != null)
                        room.setCapacity(Integer.parseInt(value));
                    break;
                case PRICE:
                    if (value != null)
                        room.setPrice(Integer.parseInt(value));
                    break;
            }
        }
        //TODO один раз в коде выше вылетело RuntimeException и в комнате
        // из списка комнат отеля изменения произошли, а просто в списке комнат - нет
        // Это произошло из-за того, что я ловлю RuntimeException и вывожу сообщение после этого
        //Что делать? Ловить и снова кидать, чтобі не произошла запись в БД?
        if (daoRoom.update(room) != null){
            if (dbManager.getDaoHotel()
                    .updateRoomInHotel(getHotelFromID(room.getHotelID()), room) != null)
                return room;
        }
        return null;
    }

    /**
     * TODO Игорю на проверку
     * Kontar Maryna:
     * The method delete room from hotel after deleting from list of all rooms
     *
     * @param room
     * @return true if the deletion of room was successful and false otherwise
     */
    public boolean deleteRoom(Room room) {

        Dao<Room> daoRoom = dbManager.getDaoRoom();
        Hotel hotel = getHotelFromID(room.getHotelID());
        return daoRoom.delete(room) && dbManager.getDaoHotel().deleteRoomFromHotel(hotel, room);
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

    public User updateUser(User user, Map<UserParameters, String> newParametersOfUser) {

        Dao<User> daoUser = dbManager.getDaoUser();

        for (Map.Entry<UserParameters, String> entry : newParametersOfUser.entrySet()) {
            String value = entry.getValue();
            switch (entry.getKey()) {
                case NAME:
                    if (value != null)
                        user.setName(value);
                    break;
                case LOGIN:
                    if (value != null)
                        user.setLogin(value);
                    break;
                case PASSWORD:
                    if (value != null)
                        user.setPassword(value);
                    break;
            }
        }
        return daoUser.update(user);
    }

    /**
     * TODO Игорю на проверку
     * Kontar Maryna:
     * The method delete User
     *
     * @param user
     * @return true if the deletion was successful and false otherwise
     */
    public boolean deleteUser(User user) {

        Dao<User> daoUser = dbManager.getDaoUser();
        return daoUser.delete(user);
    }

    public User findUserByLogin(String login) {

        List<User> allUsers = dbManager.getDaoUser().getAll();

        Optional<User> optional = allUsers.stream()
                .filter((User user) -> user.getLogin().equals(login))
                .findFirst();
        return optional.orElse(null);

    }

    /**
     * TODO Эта функция написана мной, п.ч. изначально так поделили задания с Гийомом,
     * TODO но потом мне надо было реализовывать админ меню, а функции остались
     * TODO Возможно надо написать функцию isFreeRoom(){return !isBooked}.
     * TODO потому что отрицания воспринимаются мозгом намного медленнее
     * Kontar Maryna:
     *
     * @param reserveOnUser
     * @param room
     * @param dataOfArrival
     * @param dateOfDeparture
     * @return Reservation if reservation is created
     * @throws RoomIsReservedForTheseDatesException
     */
    /*public Reservation reserveRoom(User reserveOnUser, Room room, LocalDate dataOfArrival, LocalDate dateOfDeparture)
            throws RoomIsReservedForTheseDatesException {
        Dao<Reservation> daoReservation = dbManager.getDaoReservation();

        if (room.getHotelID()
                .getRooms()
                .stream()
                .noneMatch(roomAtHotel -> !isBooked(roomAtHotel, dataOfArrival, dateOfDeparture))) {
            throw new RoomIsReservedForTheseDatesException("The room " + room + "is reserved for these dates: "
                    + dataOfArrival + " - " + dateOfDeparture);
        }
        return daoReservation.create(new Reservation(reserveOnUser, room, dataOfArrival, dateOfDeparture));

        //TODO Функция должна быть с входными параметрами.
        //TODO Метод create сохранит этот reservation в БД и добавит в список бронирований данного user?
        // Спросила у ребят из backend. Жду, пока они дойдут до этого
    }*/

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


        // update room with new booking list:

        Room myRoom = getRoomFromID(reservation.getRoomID());

        List<Reservation> myBookings = myRoom.getBookings();
        myBookings.remove(reservation);
        myRoom.setBookings(myBookings);

        return daoReservation.delete(reservation);
    }


    //TODO Нет проверки на null (isEmpty). Проверяю при вызове этого метода.
    // Если поменяю сигнатуру на Hotel findHotelByHotelName(String hotelName)
    // будет кидать NoSuchElementException("No value present") (.findFirst().get();)
    public List<Hotel> findHotelByHotelName(String hotelName) {

        List<Hotel> allHotels = dbManager.getDaoHotel().getAll();

        return allHotels.stream()
                .filter((Hotel hotel) -> hotel.getName().equalsIgnoreCase(hotelName))
                .collect(Collectors.toList());
//                .findFirst().get();
    }


// ************************************* GUILLAUME ********************************************

    public void findHotelByHotelName() {

        Dao<Hotel> daoHotel = dbManager.getDaoHotel();
        List<Hotel> allHotels = daoHotel.getAll();

        String hotelName = ConsoleWorkerUtil.readNameFromConsole("hotel name");

        List<Hotel> myHotels = allHotels.stream()
                .filter((Hotel hotel) -> hotel.getName().equalsIgnoreCase(hotelName))
                .collect(Collectors.toList());

        if (myHotels.size() == 0) System.out.println("No hotel matching your criteria");
        else {
            System.out.println("Here is a list of hotels matching your criteria: \n");
            System.out.println(Hotel.getOutputHeader());
            for (Hotel hotel : myHotels) {
                System.out.println(hotel.getOutput());
            }

            System.out.println("\nTo book a room, please choose the 'book a room' or 'search hotel by" +
                    "city and dates' options in the main menu");
        }
    }

    public SearchResults findHotelByCityDate() {

        String cityName = ConsoleWorkerUtil.readNameFromConsole("city name");
        LocalDate checkin = ConsoleWorkerUtil.getCheckinDate();
        LocalDate checkout = ConsoleWorkerUtil.getCheckoutDate(checkin);

        List<Room> rooms = searchRoomByCityDate(cityName, checkin, checkout);
        SearchResults results = new SearchResults(checkin, checkout, rooms);

        if (rooms.size() != 0) printRoomResults(rooms, checkin, checkout);

        return results;
    }

    public boolean isBooked(Room room, LocalDate checkin, LocalDate checkout) {
        List<Reservation> bookings;

        bookings = room.getBookings();

        return isBookedUpdate(bookings, checkin, checkout);
    }

    public boolean isBookedUpdate (List<Reservation> bookings, LocalDate checkin, LocalDate checkout){
        // added this function for the updateBooking method.
        boolean isBooked = false;

        if (bookings.size() == 0) {
            isBooked = false;
        } else {
            for (Reservation booking : bookings) {
                // if checkin or checkout dates are during an existing stay
                if ((booking.getCheckIn().isBefore(checkin) &&
                        (booking.getCheckOut().isAfter(checkin)))
                        || (booking.getCheckIn().isBefore(checkout) &&
                        (booking.getCheckOut().isAfter(checkout)))) isBooked = true;

                // if checkin or checkout dates are the same as an existing stay
                if ((booking.getCheckIn().isEqual(checkin))
                        || (booking.getCheckOut().isEqual(checkout))) isBooked = true;

                // if checkin before and checkout after checkin and checkout of existing stay
                if ((checkin.isBefore(booking.getCheckIn()))
                        && (checkout.isAfter(booking.getCheckOut()))) isBooked = true;
            }
        }

        return isBooked;
    }

    public SearchResults findRoomByCityDate() throws NullSearchResultsException {
        List<Room> rooms;

        String cityName = readNameFromConsole("city name");
        LocalDate checkin = getCheckinDate();
        LocalDate checkout = getCheckoutDate(checkin);

        rooms = searchRoomByCityDate(cityName, checkin, checkout);

        if (rooms.size() != 0) printRoomResults(rooms, checkin, checkout);

        SearchResults results = new SearchResults(checkin, checkout, rooms);

        return results;
    }

    public Hotel getHotelFromID(long hotelID) {

        Dao<Hotel> daoHotel = dbManager.getDaoHotel();
        List<Hotel> allHotels = daoHotel.getAll();

        Hotel myHotel = allHotels.stream()
                .filter((Hotel hotel) -> hotel.getId() == hotelID)
                .findFirst().get();

        return myHotel;
    }

    public Room getRoomFromID(long roomID) {

        Dao<Room> daoRoom = dbManager.getDaoRoom();
        List<Room> allRooms = daoRoom.getAll();

        Room myRoom = allRooms.stream()
                .filter((Room room) -> room.getId() == roomID)
                .findFirst().get();

        return myRoom;
    }


    public List<Room> searchRoomByCityDate(String cityName, LocalDate checkin, LocalDate checkout) {

        Dao<Hotel> daoHotel = dbManager.getDaoHotel();
        List<Hotel> allHotels = daoHotel.getAll();
        List<Room> rooms = new ArrayList<>();

        List<Hotel> cityHotels = allHotels.stream()
                .filter((Hotel hotel) -> hotel.getCity().equalsIgnoreCase(cityName))
                .collect(Collectors.toList());

        // create room array with all rooms in the city
        for (Hotel hotel : cityHotels) {
            rooms.addAll(hotel.getRooms());
        }

        // delete room if it is booked during requested period
        rooms.removeIf(room -> isBooked(room, checkin, checkout));

        return rooms;
    }


    public SearchResults findRoomByHotelDate() throws NullSearchResultsException {

        Dao<Hotel> daoHotel = dbManager.getDaoHotel();
        List<Hotel> allHotels = daoHotel.getAll();

        List<Hotel> myHotels;
        List<Room> rooms = new ArrayList<>();

        String hotelName = readNameFromConsole("hotel name");
        LocalDate checkin = getCheckinDate();
        LocalDate checkout = getCheckoutDate(checkin);

        myHotels = allHotels.stream()
                .filter((Hotel hotel) -> hotel.getName().equalsIgnoreCase(hotelName))
                .collect(Collectors.toList());

        // create room array with all rooms in the hotel
        for (Hotel hotel : myHotels) {
            rooms.addAll(hotel.getRooms());
        }

        // delete room if it is booked during requested period
        rooms.removeIf(room -> isBooked(room, checkin, checkout));

        if (rooms.size() != 0) printRoomResults(rooms, checkin, checkout);

        SearchResults results = new SearchResults(checkin, checkout, rooms);

        return results;

    }

    public void printRoomResults(List<Room> rooms, LocalDate checkin, LocalDate checkout) {

        // create array of hotels with available rooms from the room array
        List<Hotel> hotelDuplicates = new ArrayList<>();
        Hotel myHotel;
        for (Room room : rooms) {
            myHotel = getHotelFromID(room.getHotelID());
            hotelDuplicates.add(myHotel);
        }

        // removing duplicates
        Set<Hotel> hotelsNoD = new HashSet<Hotel>();
        hotelsNoD.addAll(hotelDuplicates);

        List<Hotel> hotelsByCityByDate = new ArrayList<>();
        hotelsByCityByDate.addAll(hotelsNoD);

        // here it would be good to print the hotels without the rooms, do this later
        System.out.println("\nHere is a list of hotels with rooms available from "
                + checkin + " to " + checkout + ":");


        // printing results in a clean way, showing only available rooms
        // i is used to as a reference number for booking function, in case they want to book a room
        final int[] i = {1};
        final Hotel[] roomHotel = new Hotel[1];
        hotelsByCityByDate.forEach(hotel -> {
            System.out.println("\n" + hotel.getName() + ", " + hotel.getCity() + ":");
            System.out.println("#" + "   " + Room.getOutputHeader());
            rooms.forEach(room -> {
                roomHotel[0] = getHotelFromID(room.getHotelID());
                if ((roomHotel[0].getId() == hotel.getId())) {
                    System.out.println(i[0] + "    " + room.getOutput());
                    //System.out.println("   " + i[0] + ": Room name: " + room.getRoomClass() + "; # of guests: " +
                    //        room.getCapacity() + "; Price per night: " + room.getPrice() + ".");
                    i[0]++;
                }
            });
        });
    }

    public void bookRoom(SearchResults results, Session session) {

        LocalDate checkin = results.getCheckin();
        LocalDate checkout = results.getCheckout();
        List<Room> rooms = results.getRooms();
        int roomChoice;

        System.out.println("Please enter the number of the room you would like to book from the list:");
        printRoomResults(rooms, checkin, checkout);

        roomChoice = getMenuInput(1, rooms.size()) - 1;

        Room room = rooms.get(roomChoice);

        // if user not logged in, prompt him to login:

        Reservation newBook = new Reservation(session.getUser(), room.getId(), checkin, checkout);

        // add booking to DAO
        Dao<Reservation> daoR = dbManager.getDaoReservation();
        daoR.create(newBook);

        // add booking to room
        List<Reservation> roomBooking = room.getBookings();
        roomBooking.add(newBook);
        room.setBookings(roomBooking);

        Hotel hotel = getHotelFromID(room.getHotelID());

        System.out.println("Congratulations, your room is booked!");
        System.out.println("\nHere is a summary of your booking:");
        System.out.println("Booking name: " + newBook.getUser().getName() + "\nHotel: " +
                hotel.getName() + ";\nRoom: " + room +
                "\nCheck-in Date: " + newBook.getCheckIn() + "\nCheckout date:" + newBook.getCheckOut() + ".");

        System.out.println("Thank you for using our services to book your stay!");
    }

    public List<Room> findRoomsInHotel(Hotel hotel) {

        return hotel.getRooms();

    }

    public Session login(Session session) {
        String userName;
        String pass;
        User user;

        if (!session.isGuest()) System.out.println("You already logged in as " + session.getUser().getLogin());

        if (session.isGuest()) {
            // get login credentials from user
            userName = readNameFromConsole("your username");
            pass = readNameFromConsole("your password");

            // try to login
            user = loginAndPasswordVerification(userName, pass);
            if (user == null) System.out.println("Wrong login credentials: please either register or try again");
            else {
                // update session info
                session.setUser(user);
                session.setGuest(false);

                if (user.getRole() == ADMIN) session.setAdmin(true);
            }
        }

        return session;
    }


    public Session logout(Session session) {
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
//                try {
                yn = readStringFromConsole();
                break;
//                } catch (IOException e) {
//                    System.out.println("You entered a wrong input, please try again");
//                    continue;
//                }
            }
            if (yn.equalsIgnoreCase("Y")) ok = true;

            // check if user exists, if not create new user in DAO
            List<User> allUsers = dbManager.getDaoUser().getAll();
            String finalName = name;
            String finalUserName = userName;
            if (allUsers.stream()
                    .anyMatch((User o) -> o.getName().equalsIgnoreCase(finalName) ||
                            o.getLogin().equals(finalUserName))) {
                System.out.println("An account with this name and / or login already exists. " +
                        "Please try again");
                ok = false;
            }
        }

        newUser = new User(name, userName, password);
        // procedure to save user to DB
        Dao<User> daoUser = dbManager.getDaoUser();
        daoUser.create(newUser);

        return newUser;

    }

    public User updateUser(User user) {

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

    public List<Reservation> getUsersBookings(User user){

        Dao<Reservation> daoRes = dbManager.getDaoReservation();
        List<Reservation> allRes = daoRes.getAll();

        List<Reservation> myBookings = allRes.stream()
                .filter((Reservation r) -> r.getUser().getId() == user.getId())
                .collect(Collectors.toList());

        return myBookings;
    }

    public Map<Integer, Reservation> createReservationMap (List<Reservation> myBookings){

        Map<Integer, Reservation> mapOfEntities = new HashMap<>(myBookings.size());

        int i = 0;
        for (Reservation entity : myBookings) {
            i = i + 1;
            mapOfEntities.put(i, entity);
        }

        return mapOfEntities;
    }

    public void printUserBookings(Map<Integer, Reservation> mapOfBookings){

        System.out.println("\t" + Reservation.getOutputHeader() + "    Hotel Name" + "    City");
        mapOfBookings.forEach((key, value) -> System.out.println("[" + key + "]: " + value.getOutput() + "     " + getHotelNameFromBooking(value)+ "       " + getCityNameFromBooking(value) + "\n"));

    }

    public void updateBooking(Map<Integer, Reservation> mapOfBookings){

        String yn;
        boolean ok = false;

        Reservation myBooking = chooseBookingFromList(mapOfBookings);
        LocalDate checkIn = getCheckinDate();
        LocalDate checkOut = getCheckoutDate(checkIn);

        // get list of bookings for the room:
        Dao<Reservation> daoR = dbManager.getDaoReservation();
        List<Reservation> allBookings = daoR.getAll();

        List<Reservation> bookings = allBookings.stream()
                .filter(r -> r.getRoomID() == myBooking.getRoomID())
                .collect(Collectors.toList());


        boolean canUpdate = false;
        if (bookings.size()==1){
            canUpdate = true;
        }
        else {
            Reservation oldBooking = bookings
                    .stream()
                    .filter(r -> r.getId() == myBooking.getId())
                    .findFirst()
                    .get();
            bookings.remove(oldBooking);
            if (!isBookedUpdate(bookings, checkIn, checkOut)) canUpdate = true;
        }


        // check availability of room with new checkin and checkout dates:

        if (canUpdate)

        {
            System.out.println("We are about to change your booking. Your new check-in date will be" + checkIn
                    + "and your new check-out date will be" + checkOut +". \n"+
                    "If you want to proceed, please enter Y, else press any key");

            while (true) {
                yn = readStringFromConsole();
                break;
            }
            if (yn.equalsIgnoreCase("Y")) ok = true;

                if (ok){
                    myBooking.setCheckIn(checkIn);
                    myBooking.setCheckOut(checkOut);

                    // add booking to room
                    bookings.add(myBooking);
                    Room myRoom = getRoomFromID(myBooking.getRoomID()); // this gets a room object
                    myRoom.setBookings(bookings); // but this set method works but is not written in DB...
                    updateDB(); // the update DB function gets the right room info with right bookings, but the DB is not updated
                    
                    Hotel myHotel = getHotelFromID(myRoom.getHotelID());

                    System.out.println("Congratulations, your booking has been updated");
                    System.out.println("\nHere is a summary of your booking:");
                    System.out.println("Booking name: " + myBooking.getUser().getName() + "\nHotel: " +
                            myHotel.getName() + ";\nRoom: " + myRoom +
                            "\nCheck-in Date: " + myBooking.getCheckIn() + "\nCheckout date:" + myBooking.getCheckOut() + ".");

                    System.out.println("Thank you for using our services to book your stay!");
                }

        } else System.out.println("Unfortunately, the room is not available for your new dates");
    }

    public String getHotelNameFromBooking(Reservation booking) {

        Room myRoom = getRoomFromID(booking.getRoomID());

        Hotel myHotel = getHotelFromID(myRoom.getHotelID());

        return myHotel.getName();
    }


    public String getCityNameFromBooking(Reservation booking) {

        Room myRoom = getRoomFromID(booking.getRoomID());

        Hotel myHotel = getHotelFromID(myRoom.getHotelID());

        return myHotel.getCity();
    }

    public Reservation chooseBookingFromList(Map<Integer, Reservation> mapOfRes){

        System.out.println("Please choose the number of the booking you want to change: ");
        int resKey = readIntToMaxNum(mapOfRes.size());
        Reservation myBooking = mapOfRes.get(resKey);

        return myBooking;
    }

    public void updateDB() {
        dbManager.updateDatabase();
    }

}
