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
     * Checks the presence of the user with the appropriate login and password
     * @param login of the user whose presence is to be tested
     * @param password of the user whose presence is to be tested
     * @return <tt>User</tt> if user with the appropriate login and password exist
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
     * Adds the hotel to the database, if the hotel isn't in the database
     *
     * @param hotel to be appended to database
     * @return the added hotel if it isn't exist
     * @throws HotelAlreadyExistsException if the <tt>hotel</tt> already exists in database
     */
    public Hotel addHotel(Hotel hotel) throws HotelAlreadyExistsException {

        Dao<Hotel> daoHotel = dbManager.getDaoHotel();
        List<Hotel> allHotels = daoHotel.getAll();

        if (allHotels.stream().anyMatch(hotelAtDatabase -> hotelAtDatabase.equals(hotel))) {
            throw new HotelAlreadyExistsException("The " + hotel + "already exists in database "
                    + dbManager.getClass().getSimpleName());
        }
        return daoHotel.create(hotel);
    }

    /**
     * Deletes hotel and return true if the deletion was successful
     *
     * @param hotel to be deleted from database
     * @return true if the deletion was successful and false otherwise
     */
    public boolean deleteHotel(Hotel hotel) {

        Dao<Hotel> daoHotel = dbManager.getDaoHotel();
        return daoHotel.delete(hotel);
    }

    /**
     * Updates the hotel with <code>newParametersOfHotel</code>
     * (HotelParameters.NAME, HotelParameters.CITY, HotelParameters.RATING)
     *
     * @param hotel to be updated
     * @param newParametersOfHotel map of new parameters
     *                             (HotelParameters.NAME, HotelParameters.CITY, HotelParameters.RATING) to update hotel
     * @return updated hotel
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
     * Creates room if room isn't in database
     *
     * @param room to be appended to database
     * @return created room if it isn't exist
     * @throws RoomAlreadyExistsException if room already exists in database
     */
    public Room createRoom(Room room) throws RoomAlreadyExistsException {

        Dao<Room> daoRoom = dbManager.getDaoRoom();
        List<Room> allRooms = daoRoom.getAll();

        if (allRooms.stream()
                .anyMatch(roomAtDatabase -> roomAtDatabase.equals(room))
                )
            throw new RoomAlreadyExistsException("The " + room + "already exists in database "
                    + dbManager.getClass().getSimpleName());
        Hotel hotel = getHotelFromID(room.getHotelID());
        if (dbManager.getDaoHotel().addRoomToHotel(hotel, room) == null) return null;

        return daoRoom.create(room);
    }

    /**
     * Updates the room in the hotel after updating in list of all rooms with <code>newParametersOfRoom</code>
     * (RoomParameters.ROOM_CLASS, RoomParameters.CAPACITY, RoomParameters.PRICE)
     *
     * @param room to be updated
     * @param newParametersOfRoom map of new parameters
     *                             (RoomParameters.ROOM_CLASS, RoomParameters.CAPACITY, RoomParameters.PRICE) to update the room
     * @return updated room
     */
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

        if (daoRoom.update(room) != null) {
            if (dbManager.getDaoHotel()
                    .updateRoomInHotel(getHotelFromID(room.getHotelID()), room) != null)
                return room;
        }
        return null;
    }

    /**
     * Deletes room from hotel after deleting from list of all rooms
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
     * Create account for user with name, login and password
     * if there isn't account with this name and login.
     *
     * @param name
     * @param login
     * @param password
     * @return User if an account is created
     * @throws AccountAlreadyExistException if account with these name or login already exists
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
     * Updates the user in the reservation after updating in list of all reservations with <code>newParametersOfUser</code>
     * (UserParameters.NAME, UserParameters.LOGIN, UserParameters.PASSWORD)
     *
     * @param user to be updated
     * @param newParametersOfUser map of new parameters
     *                            (UserParameters.NAME, UserParameters.LOGIN, UserParameters.PASSWORD) to update the user
     * @return updated user
     */
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

        User updatedUser = daoUser.update(user);
        List<Reservation> userReservation = userReservations(updatedUser);
        userReservation.forEach(reservation -> updateReservationToUpdateUser(reservation, updatedUser));
        return updatedUser;
    }

    /**
     * Updates user in user reservation
     * @param reservation whose user is updated
     * @param user updated user
     * @return reservation with updated user
     */
    private Reservation updateReservationToUpdateUser(Reservation reservation, User user) {

        reservation.setUser(user);
        Room room = getRoomFromID(reservation.getRoomID());
        room.getBookings().removeIf(res -> res.getId() == reservation.getId());
        room.getBookings().add(reservation);

        if (dbManager.getDaoHotel()
                .updateRoomInHotel(getHotelFromID(room.getHotelID()), room) != null)
            return reservation;

        return null;
    }

    /**
     * Deletes user and all its reservations
     *
     * @param user to be deleted from database
     * @return true if the deletion was successful and false otherwise
     */
    public boolean deleteUser(User user) {

        Dao<User> daoUser = dbManager.getDaoUser();
        List<Reservation> userReservation = userReservations(user);
        userReservation.forEach(reservation -> cancelRoomReservation(reservation));//будут проблемы, если cancelRoomReservation будет false. Продумать проверку
        return daoUser.delete(user);
    }

    /**
     * Creates list of all user reservations
     *
     * @param user whose reservations need to find
     * @return list of user reservations
     */
    private List<Reservation> userReservations(User user) {

        Dao<Reservation> daoReservation = dbManager.getDaoReservation();
        List<Reservation> listReservation = daoReservation.getAll();

        if (listReservation.isEmpty()) return null;

        return listReservation.stream()
                .filter(reservation -> reservation.getUser().getId() == user.getId())
                .collect(Collectors.toList());
    }

    /**
     * Finds user by user login
     *
     * @param login to find user
     * @return user if login exists or null otherwise
     */
    public User findUserByLogin(String login) {

        List<User> allUsers = dbManager.getDaoUser().getAll();

        Optional<User> optional = allUsers.stream()
                .filter((User user) -> user.getLogin().equals(login))
                .findFirst();
        return optional.orElse(null);

    }

    /**
     * Deletes room reservation
     *
     * @param reservation
     * @return true if the deletion was successful and false otherwise
     */
    public boolean cancelRoomReservation(Reservation reservation) {

        Dao<Reservation> daoReservation = dbManager.getDaoReservation();

        // update room with new booking list:
        Room myRoom = getRoomFromID(reservation.getRoomID());
        Hotel myHotel = getHotelFromID(myRoom.getHotelID());

        List<Reservation> myBookings = myRoom.getBookings();
        myBookings.remove(reservation);
        myRoom.setBookings(myBookings);

        //myRoom = getRoomFromID(myBooking.getRoomID()); // this gets a room object
        if (dbManager.getDaoHotel().updateRoomInHotel(myHotel, myRoom) != null) {
            return daoReservation.delete(reservation);
        } else return false;
    }

    /**
     * Finds hotel by hotels name
     * @param hotelName
     * @return list of hotels
     */
    public List<Hotel> findHotelByHotelName(String hotelName) {

        List<Hotel> allHotels = dbManager.getDaoHotel().getAll();

        return allHotels.stream()
                .filter((Hotel hotel) -> hotel.getName().equalsIgnoreCase(hotelName))
                .collect(Collectors.toList());
//                .findFirst().get();
    }


// ************************************* GUILLAUME ********************************************

    /**Identifies and prints out a list of hotels matching the hotel name inserted by user, by calling other methods.
     * @return a list of hotels matching the hotel name, inserted by the user.
     * @see ConsoleWorkerUtil#readNameFromConsole(String)
     * @see Hotel#getOutputHeader()
     * @see Hotel#getOutput()
     */
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
    /**Identifies and prints out a list of rooms available in a specific city during the period of time inserted by user, by calling other methods.
     * @return a list of rooms available in a specific city during the period of time inserted by user;
     * @see #searchRoomByCityDate(String, LocalDate, LocalDate)
     * @see ConsoleWorkerUtil#readNameFromConsole(String)
     * @see ConsoleWorkerUtil#getCheckinDate()
     * @see ConsoleWorkerUtil#getCheckoutDate(LocalDate)
     * @see #printRoomResults(List, LocalDate, LocalDate)
     */
    public SearchResults findHotelByCityDate() {

        String cityName = ConsoleWorkerUtil.readNameFromConsole("city name");
        LocalDate checkin = ConsoleWorkerUtil.getCheckinDate();
        LocalDate checkout = ConsoleWorkerUtil.getCheckoutDate(checkin);

        List<Room> rooms = searchRoomByCityDate(cityName, checkin, checkout);
        SearchResults results = new SearchResults(checkin, checkout, rooms);

        if (rooms.size() != 0) printRoomResults(rooms, checkin, checkout);

        return results;
    }

    /**Creates a list of reservations for a specific room and calls a <code>isBookedUpdate(bookings, checkin, checkout)</code> method.
     * @param room a particular booked <code>Room</code>
     * @param checkin a date of check-in
     * @param checkout a date of check-out
     * @return Returns a <code>isBookedUpdate(bookings, checkin, checkout)</code> method
     * @see Room#getBookings()
     */
    public boolean isBooked(Room room, LocalDate checkin, LocalDate checkout) {
        List<Reservation> bookings;

        bookings = room.getBookings();

        return isBookedUpdate(bookings, checkin, checkout);
    }
    /**Identifies if a particular <code>Room</code> is available during a period of time specified by the check-in and check-out dates.
     * @param bookings list of bookings
     * @param checkin a date of check-in
     * @param checkout a date of check-out
     * @return Returns <code>False</code> if required room is available during a period of time specified by the check-in and check-out dates.
     * @see Room#getBookings()
     */
    public boolean isBookedUpdate(List<Reservation> bookings, LocalDate checkin, LocalDate checkout) {
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
    /**Identifies and prints out a list of rooms available in a specific city during the period of time inserted by user, by calling other methods.
     * @return a list of rooms available in a specific city during the period of time inserted by user;
     * @see #searchRoomByCityDate(String, LocalDate, LocalDate)
     * @see ConsoleWorkerUtil#readNameFromConsole(String)
     * @see ConsoleWorkerUtil#getCheckinDate()
     * @see ConsoleWorkerUtil#getCheckoutDate(LocalDate)
     * @see #printRoomResults(List, LocalDate, LocalDate)
     */
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
    /**Identifies and prints out a list of rooms available in a specific city during the period of time inserted by user.
     * @return a list of rooms available in a specific city during the period of time inserted by user;
     * @see #findRoomByCityDate()
     * @see #findHotelByCityDate()
     * @see ConsoleWorkerUtil#readNameFromConsole(String)
     * @see ConsoleWorkerUtil#getCheckinDate()
     * @see ConsoleWorkerUtil#getCheckoutDate(LocalDate)
     * @see #printRoomResults(List, LocalDate, LocalDate)
     */
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
    /**Identifies and prints out a list of rooms available in a specific hotel during the period of time inserted by user, by calling other methods.
     * @return a list of rooms available in a specific hotel during the period of time inserted by user;
     * @see ConsoleWorkerUtil#readNameFromConsole(String)
     * @see ConsoleWorkerUtil#getCheckinDate()
     * @see ConsoleWorkerUtil#getCheckoutDate(LocalDate)
     * @see #printRoomResults(List, LocalDate, LocalDate)
     */
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

        // create map of rooms

        Map<Integer, Room> mapOfRooms = new HashMap<>(rooms.size());

        int i = 0;
        for (Room room : rooms) {
            i = i + 1;
            mapOfRooms.put(i, room);
        }


        // create array of hotels with available rooms from the room array
        List<Hotel> hotelDuplicates = new ArrayList<>();
        Hotel myHotel;
        for (Room room : rooms) {
            myHotel = getHotelFromID(room.getHotelID());
            hotelDuplicates.add(myHotel);
        }

        // removing duplicates
        Set<Hotel> hotelsNoD = new HashSet<>();
        hotelsNoD.addAll(hotelDuplicates);

        List<Hotel> hotelsByCityByDate = new ArrayList<>();
        hotelsByCityByDate.addAll(hotelsNoD);

        System.out.println("\nHere is a list of hotels with rooms available from "
                + checkin + " to " + checkout + ":");
        // printing results in a clean way, showing only available rooms
        // i is used to as a reference number for booking function, in case they want to book a room
        final Hotel[] roomHotel = new Hotel[1];
        hotelsByCityByDate.forEach(hotel -> {
            System.out.println("\n" + hotel.getName() + ", " + hotel.getCity() + ":");
            System.out.println("#" + "   " + Room.getOutputHeader());

            mapOfRooms.forEach((key, value) -> {
                roomHotel[0] = getHotelFromID(value.getHotelID());
                if ((roomHotel[0].getId() == hotel.getId())) {
                    System.out.println("[" + key + "]: " + value.getOutput());
                }
            });
        });
    }

    public void bookRoom(SearchResults results, Session session) {
        if (results == null || session == null) {
            System.out.println("Room booking error");
            return;
        }

        LocalDate checkin = results.getCheckin();
        LocalDate checkout = results.getCheckout();

        List<Room> rooms = results.getRooms(); //КОМНАТЫ ИЗ ОТЕЛЕЙ
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

        /***************/
        Dao<Room> daoRoom = dbManager.getDaoRoom();
        List<Room> listRooms = daoRoom.getAll();
        Optional<Room> optional = listRooms.stream().filter(i -> room.getId() == i.getId()).findFirst();
        Room roomInListOfRooms = null;
        if (optional.isPresent())
            roomInListOfRooms = optional.get();
        roomInListOfRooms.getBookings().add(newBook);
        /***************/


        System.out.println("Congratulations, your room is booked!");
        System.out.println("\nHere is a summary of your booking:");
        System.out.println("\nBooking name: " + newBook.getUser().getName() + "\nHotel: " +
                hotel.getName() + "\nCheck-in Date: " + newBook.getCheckIn() +
                "\nCheckout date:" + newBook.getCheckOut() + ".");
        System.out.println("\nRoom information:");
        System.out.println(Room.getOutputHeader());
        System.out.println(room.getOutput());

        System.out.println("\nThank you for using our services to book your stay!");
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

    public List<Reservation> getUsersBookings(User user) {

        Dao<Reservation> daoRes = dbManager.getDaoReservation();
        List<Reservation> allRes = daoRes.getAll();

        List<Reservation> myBookings = allRes.stream()
                .filter((Reservation r) -> r.getUser().getId() == user.getId())
                .collect(Collectors.toList());

        return myBookings;
    }

    public Map<Integer, Reservation> createReservationMap(List<Reservation> myBookings) {

        Map<Integer, Reservation> mapOfEntities = new HashMap<>(myBookings.size());

        int i = 0;
        for (Reservation entity : myBookings) {
            i = i + 1;
            mapOfEntities.put(i, entity);
        }

        return mapOfEntities;
    }

    public void printUserBookings(Map<Integer, Reservation> mapOfBookings) {

        System.out.println("\t" + Reservation.getOutputHeader() + "    Hotel Name" + "    City");
        mapOfBookings.forEach((key, value) -> System.out.println("[" + key + "]: " + value.getOutput() + "     " + getHotelNameFromBooking(value) + "       " + getCityNameFromBooking(value) + "\n"));

    }

    public void updateBooking(Map<Integer, Reservation> mapOfBookings) {

        String yn;
        boolean ok = false;

        Reservation myBooking = chooseBookingFromList(mapOfBookings);
        LocalDate checkIn = getCheckinDate();
        LocalDate checkOut = getCheckoutDate(checkIn);

        // get list of bookings for the room:
        Dao<Reservation> daoR = dbManager.getDaoReservation();
        List<Reservation> allBookings = daoR.getAll();

        Dao<Hotel> daoMyHotels = dbManager.getDaoHotel();

        List<Reservation> bookings = allBookings.stream()
                .filter(r -> r.getRoomID() == myBooking.getRoomID())
                .collect(Collectors.toList());


        boolean canUpdate = false;
        if (bookings.size() == 1) {
            canUpdate = true;
        } else {
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
            System.out.println("We are about to change your booking. Your new check-in date will be " + checkIn
                    + "and your new check-out date will be " + checkOut + ". \n" +
                    "If you want to proceed, please enter Y, else press any key");

            while (true) {
                yn = readStringFromConsole();
                break;
            }
            if (yn.equalsIgnoreCase("Y")) ok = true;

            if (ok) {

                //delete existing booking and updateDB
                Room myRoom = getRoomFromID(myBooking.getRoomID()); // this gets a room object
                Hotel myHotel = getHotelFromID(myRoom.getHotelID());


                //myRoom.setBookings(bookings); // but this set method works but is not written in DB...
                //updateDB();

                myBooking.setCheckIn(checkIn);
                myBooking.setCheckOut(checkOut);

                // add booking to room
                bookings.clear();
                bookings.add(myBooking);
                //myRoom = getRoomFromID(myBooking.getRoomID()); // this gets a room object
                myRoom.setBookings(bookings);
                myHotel = dbManager.getDaoHotel().updateRoomInHotel(myHotel, myRoom);


                // but this set method works but is not written in DB...
                updateDB(); // the update DB function gets the right room info with right bookings, but the DB is not updated

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

    public Reservation chooseBookingFromList(Map<Integer, Reservation> mapOfRes) {

        System.out.println("Please choose the number of the booking you want to change: ");
        int resKey = readIntToMaxNum(mapOfRes.size());
        Reservation myBooking = mapOfRes.get(resKey);

        return myBooking;
    }

    public void updateDB() {
        dbManager.updateDatabase();
    }

}
