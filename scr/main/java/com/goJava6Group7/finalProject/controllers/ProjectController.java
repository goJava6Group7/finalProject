package com.goJava6Group7.finalProject.controllers;

import com.goJava6Group7.finalProject.data.dao.Dao;
import com.goJava6Group7.finalProject.data.dataBase.DataBaseManager;
import com.goJava6Group7.finalProject.entities.*;
import com.goJava6Group7.finalProject.exceptions.frontend.*;
import com.goJava6Group7.finalProject.utils.ConsoleWorkerUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
     * Kontar Maryna:
     * Method create account for user with name, login and password
     * if there isn't account with this name and login.
     * Returns the user if an account is created
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
     * Kontar Maryna:
     *
     * @param reserveOnUser
     * @param room
     * @param dataOfArrival
     * @param dateOfDeparture
     * @return Reservation
     * @throws NoSuchRoomException1
     * @throws RoomIsReservedForTheseDatesException
     */
    public Reservation reserveRoom(User reserveOnUser, Room room, LocalDate dataOfArrival, LocalDate dateOfDeparture)
            throws FrontendException {
        Dao<Reservation> daoReservation = dbManager.getDaoReservation();
        //TODO доделать проверку по датам
        if (room.getHotel().getHotelRooms().stream().noneMatch(roomAtHotel -> true)) {
            throw new RoomIsReservedForTheseDatesException("The room is reserved for these dates: "
                    + dataOfArrival + " - " + dateOfDeparture);
        }
        return daoReservation.create(new Reservation(reserveOnUser, room, dataOfArrival, dateOfDeparture));

        //TODO Функция должна быть с входными параметрами.
        //TODO Метод create сохранит этот reservation в БД и добавит в список бронирований данного user?
        // Спросила у ребят из backend. Жду, пока они дойдут до этого
    }

    public boolean cancelRoomReservation() {
        throw new UnsupportedOperationException();
    }


    /**
     * TODO Игорю на проверку
     * TODO(Замечания) - определится с тем, что будет считаться идентичным Отелем
     * Kontar Maryna:
     *
     * @param hotel
     * @return
     * @throws HotelAlreadyExistsException
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
     * TODO Игорю на проверку
     * Kontar Maryna:
     * The method delete hotel and return true if the delete succeeded
     * @param hotel
     * @return true if the delete succeeded and false otherwise
     */
    public boolean deleteHotel(Hotel hotel) {

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
     * TODO дописать javaDoc
     * Kontar Maryna:
     *
     * @param room
     * @return
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
     *  TODO Решить, что будет возвращать метод (boolean, Room), подумать над проверкой на существование комнаты в БД и дописать javaDoc
     * Kontar Maryna:
     *
     * @param room
     * @param hotel
     */
    public void addRoomToHotel(Room room, Hotel hotel){

        hotel.getHotelRooms().add(room);
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
        System.out.println("Here is a list of hotels with rooms available when you will be in " + cityName +
                " from " + checkin + " to " + checkout);
        System.out.println(hotelsByCityByDate);

        SearchResults results = new SearchResults(checkin, checkout, rooms);

        return results;

    }

    public static boolean isBooked (Room room, LocalDate checkin, LocalDate checkout){
        boolean isBooked = false;
        List<Reservation> bookings;

        bookings = room.getBookings();
        if (bookings == null) {isBooked = false;} else {
            for (Reservation booking : bookings){
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

    public List<Room> searchRoomByCityDate(String cityName, LocalDate checkin, LocalDate checkout){

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
        rooms.removeIf(room -> isBooked(room,checkin, checkout));

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
        rooms.removeIf(room -> isBooked(room,checkin, checkout));

        System.out.println("Here are the rooms available in the hotel " + hotelName +
                " from " + checkin + " to " + checkout);

        System.out.println(rooms);

        SearchResults results = new SearchResults(checkin, checkout, rooms);

        return results;

    }


    public void bookRoom(SearchResults results){

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

    public void createUser(){

        User newUser;
        String userName = "";
        String email = "";
        String password = "";
        String yn;

        boolean ok = false;

        while (!ok){
            userName = readNameFromConsole("user name");
            email = readNameFromConsole("email");
            password = readNameFromConsole("password");

            System.out.println("\nHere is a summary of your data:");
            System.out.println("Username: " + userName);
            System.out.println("Email: " + email);

            System.out.println("If this is correct, please enter Y, else press any key");

            while(true){
                try{
                    yn = readStringFromConsole();
                    break;
                }catch (IOException e){
                    System.out.println("You entered a wrong input, please try again");
                    continue;
                }
            }
            if (yn.equalsIgnoreCase("Y")) ok = true;
        }

        newUser = new User(email, userName, password);

        System.out.println(newUser);
        // here save this user to the database (and check if user already exists)

    }


}
