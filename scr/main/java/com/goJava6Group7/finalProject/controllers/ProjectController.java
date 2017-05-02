package com.goJava6Group7.finalProject.controllers;

import com.goJava6Group7.finalProject.data.dao.Dao;
import com.goJava6Group7.finalProject.data.dao.impl.DaoHotel;
import com.goJava6Group7.finalProject.data.dataBase.DataBaseManager;
import com.goJava6Group7.finalProject.entities.Hotel;
import com.goJava6Group7.finalProject.entities.Reservation;
import com.goJava6Group7.finalProject.entities.Room;
import com.goJava6Group7.finalProject.entities.User;
import com.goJava6Group7.finalProject.exceptions.frontend.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Igor on 13.04.2017.
 */
public class ProjectController {

    public DataBaseManager dbManager;

//    TODO(Answer3) - убрать все эти переменные. Все ссылки на списки сущностей в виде списков java будут хранится
//    TODO(Answer3) в обьектах DataBaseManager(это наш внутренний кеш). Все обращения к этим спискам будут непосредственно
//    TODO(Answer3) во внутренних методах нашего ProjectController

//    TODO(Answer4) - nameSpace. static final переменные должны обьявляться в верхнем регистре с разделением слов через _

//    private final static DaoHotel DAOHotel = dbManager.getDaoHotel();
//    private final static DaoUser DAOUser = dbManager.getDaoUser();
//    private final static DaoRoom DAORoom = dbManager.getDaoRoom();
//    private final static DaoReservation DAOReservation = dbManager.getDaoReservation();
//
//    private final static List<Hotel> allHotels = DAOHotel.getAll();
//    private final static List<User> allUsers = DAOUser.getAll();
//    private final static List<Room> allRooms = DAORoom.getAll();
//    private final static List<Reservation> allReservation = DAOReservation.getAll();


    // ************************* MARINA ********************************************************


    public ProjectController(DataBaseManager dbManager) {
        this.dbManager = dbManager;
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
//        TODO(Замечания) - работаем с отдельными ДАО внутри класса и НЕ показываем отдельную реализацию
//        TODO(Замечания) поэтому обьявляем переменную типом интерфейса
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

    /*
     * TODO Игорю на проверку
     * Kontar Maryna:
     * method can @throw NoSuchElementException("No value present")
     * Может надо словить это исключение и кинуть вместо него исключение, к-ое extend от FrontendException?
     * <p/>
     * return hotel if it's exist and @throw NoSuchElementException if isn't
     *
     * @param hotelName
     * @return
     * @throws NoSuchElementException
     */

    /*Поменял сигнатуру метода. Более логично принимать набор ключей-значений и в зависимости от них
    * делать поиск*/

    public List<Room> findRoom(Map<String, String> params) {
//        TODO(Замечания) - работа с консолью должна происходить ТОЛЬКО В КЛАССЕ MENU

/*
        Scanner scanner = new Scanner(System.in);
//        String roomName;
        String roomName = scanner.next();

        while (true) {
            try {

                // check if room exists
                if (allRooms.stream()
                        .filter(o -> o.getName().equals(roomName) && o.getHotel().equals(hotelName))
                        .findFirst()
                        .isPresent()) {
                    break;
                } else {
                    throw new NoSuchRoomException(hotelName);
                }

            } catch (NoSuchRoomException e) {
                continue;
            }
        }

        return allRooms.stream()
                .filter(o -> o.getName().equals(roomName) && o.getHotel().equals(hotelName))
                .findFirst()
                .get();
*/
        throw new UnsupportedOperationException();
    }

    /*
    public List <Room> findRoomsInHotelByDate(String hotelName, Date checkin, Date checkout )
    {
        // To Do
    }
    */


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
     *
     * @param hotel
     * @return
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
     * TODO Игорю на проверку НАВЕРНОЕ НАДО ПОМЕНЯТЬ СИГНАТУРУ МЕТОДА update на update(Hotel hotel, Hotel newHotel)
     * TODO(Замечания) - согласен
     * (или на update(Hotel hotel, параметры отеля))
     * Kontar Maryna:
     *
     * @param hotel
     * @param newHotel
     * @return
     * @throws HotelIsNotInDatabaseException
     */
    public Hotel updateHotel(Hotel hotel, Hotel newHotel) throws HotelIsNotInDatabaseException {
        Dao<Hotel> daoHotel = dbManager.getDaoHotel();

        //TODO(Замечания) - лишняя проверка. Если мы будем делать update, то к этому моменту уже будем знать, что отель существует
//        if (allHotels.stream().anyMatch(currentHotel -> hotel.equals(hotel))) {
//            throw new HotelIsNotInDatabaseException("The " + hotel + "is not in database "
//                    + dbManager.getClass().getSimpleName());
//        }
        return daoHotel.update(newHotel);
    }

// ************************************* GUILLAUME ********************************************


    public List <Hotel> findHotelByHotelName(String hotelName) throws NoSuchElementException {

        Dao<Hotel> daoHotel = dbManager.getDaoHotel();
        List<Hotel> allHotels = daoHotel.getAll();

        return allHotels.stream()
                .filter((Hotel hotel) -> hotel.getHotelName().equals(hotelName))
                .collect(Collectors.toList());
    }

    public List<Hotel> findHotelByCityDate(String cityName, LocalDate checkin, LocalDate checkout) throws NoSuchElementException {

        List<Room> rooms = findRoomByCityDate(cityName, checkin, checkout);

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

        // issue: when printing results, it also prints rooms that are not available... maybe better to return a
        // room map, with only available rooms, grouped by hotel?
        return hotelsByCityByDate;
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

            }
        }

        return isBooked;
    }

    public List<Room> findRoomByCityDate(String cityName, LocalDate checkin, LocalDate checkout) throws NoSuchElementException {

        Dao<Hotel> daoHotel = dbManager.getDaoHotel();
        List<Hotel> allHotels = daoHotel.getAll();
        List<Room> rooms = new ArrayList<>();

        List<Hotel> cityHotels = allHotels.stream()
                .filter((Hotel hotel) -> hotel.getHotelCity().equals(cityName))
                .collect(Collectors.toList());

        // create room array with all rooms in the city
        for (Hotel hotel : cityHotels) {
            rooms.addAll(hotel.getHotelRooms());
        }

        // delete room if it is booked during requested period
        rooms.removeIf(room -> isBooked(room,checkin, checkout));

        return rooms;
    }

    public List<Room> findRoomByHotelDate(String hotelName, LocalDate checkin, LocalDate checkout) throws NoSuchElementException {

        Dao<Hotel> daoHotel = dbManager.getDaoHotel();
        List<Hotel> allHotels = daoHotel.getAll();
        List<Hotel> myHotels;
        List<Room> rooms = new ArrayList<>();


        myHotels = allHotels.stream()
                .filter((Hotel hotel) -> hotel.getHotelName().equals(hotelName))
                .collect(Collectors.toList());

        // create room array with all rooms in the city
        for (Hotel hotel : myHotels) {
            rooms.addAll(hotel.getHotelRooms());
        }

        // delete room if it is booked during requested period
        rooms.removeIf(room -> isBooked(room,checkin, checkout));

        System.out.println("Here are the rooms available during your stay:");
        System.out.println(rooms);

        return rooms;
    }

    public List<Room> findRoomsInHotel(Hotel hotel) {

        return hotel.getHotelRooms();

    }


}
