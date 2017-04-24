package com.goJava6Group7.finalProject.controllers;

import com.goJava6Group7.finalProject.data.dao.impl.DaoHotel;
import com.goJava6Group7.finalProject.data.dao.impl.DaoReservation;
import com.goJava6Group7.finalProject.data.dao.impl.DaoRoom;
import com.goJava6Group7.finalProject.data.dao.impl.DaoUser;
import com.goJava6Group7.finalProject.data.dataBase.DataBaseManager;
import com.goJava6Group7.finalProject.entities.Hotel;
import com.goJava6Group7.finalProject.entities.Reservation;
import com.goJava6Group7.finalProject.entities.Room;
import com.goJava6Group7.finalProject.entities.User;
import com.goJava6Group7.finalProject.exceptions.frontend.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Igor on 13.04.2017.
 */
public class ProjectController {

    private static DataBaseManager dbManager;

    private final static DaoHotel DAOHotel = dbManager.getDaoHotel();
    private final static DaoUser DAOUser = dbManager.getDaoUser();
    private final static DaoRoom DAORoom = dbManager.getDaoRoom();
    private final static DaoReservation DAOReservation = dbManager.getDaoReservation();

    private final static List<Hotel> allHotels = DAOHotel.getAll();
    private final static List<User> allUsers = DAOUser.getAll();
    private final static List<Room> allRooms = DAORoom.getAll();
    private final static List<Reservation> allReservation = DAOReservation.getAll();

    public ProjectController(DataBaseManager dbManager) {
        ProjectController.dbManager = dbManager;
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
     * @return
     */
    public User createAccount(String name, String login, String password) throws AccountAlreadyExistException {

        User user = new User(name, login, password);
        if (allUsers.stream()
                .anyMatch((User o) -> o.getName().equals(name) || o.getLogin().equals(login))) {
            throw new AccountAlreadyExistException("Account with these name or login already exists.");
        }
        DAOUser.create(user);
        return user;
    }

    /**
     * Kontar Maryna:
     *
     * @param user
     * @param room
     * @param hotel
     * @param dataOfArrival
     * @param dateOfDeparture
     * @return
     * @throws NoSuchRoomException1
     * @throws RoomIsReservedForTheseDatesException
     */
    public Reservation reserveRoom(User user, Room room, Hotel hotel, Date dataOfArrival, Date dateOfDeparture)
            throws FrontendException {

        if (hotel.getHotelRooms().stream().noneMatch(roomAtHotel -> roomAtHotel.equals(room))) {
            throw new NoSuchRoomException1("There are no such room: \n" + room + "\nin the hotel: \n" + hotel);
        }

        //TODO доделать проверку по датам
        if (hotel.getHotelRooms().stream().noneMatch(roomAtHotel -> true)) {
            throw new RoomIsReservedForTheseDatesException("The room is reserved for these dates: "
                    + dataOfArrival + " - " + dateOfDeparture);
        }

        return DAOReservation.create(new Reservation(user, room, hotel, dataOfArrival, dateOfDeparture));

        //TODO Функция должна быть с входными параметрами.
        //TODO Метод create сохранит этот reservation в БД и добавит в список бронирований данного user?
        // Спросила у ребят из backend. Жду, пока они дойдут до этого
    }

    public boolean cancelRoomReservation() {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO Игорю на проверку
     * Kontar Maryna:
     * method can @throw NoSuchElementException("No value present")
     * Может надо словить это исключение и кинуть вместо него исключение, к-ое extend от FrontendException?
     * <p>
     * return hotel if it's exist and @throw NoSuchElementException if isn't
     *
     * @param hotelName
     * @return
     * @throws NoSuchElementException
     */
    public Hotel findHotelByHotelName(String hotelName) throws NoSuchElementException {
        return allHotels.stream()
                .filter((Hotel hotel) -> hotel.getHotelName().equals(hotelName))
                .findFirst().get();
    }

    public List<Hotel> findHotelByCityName(String cityName) throws NoSuchElementException {
        // access database
        //DaoHotel hotelDAO = new DaoHotel();
        //List <Hotel> cityHotels = hotelDAO.getAll();
        return allHotels.stream()
                .filter((Hotel hotel) -> hotel.getHotelCity().equals(cityName))
                .collect(Collectors.toList());
    }

    public Room findRoomInHotel(String hotelName) {
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
    }


    public List<Room> findRoomsInHotel(Hotel hotel) {

        return hotel.getHotelRooms();

    }

    /*
    public List <Room> findRoomsInHotelByDate(String hotelName, Date checkin, Date checkout )
    {
        // To Do
    }
    */


    /**
     * TODO Игорю на проверку
     * Kontar Maryna:
     *
     * @param hotel
     * @return
     * @throws HotelAlreadyExistsException
     */
    public Hotel addHotel(Hotel hotel) throws HotelAlreadyExistsException {

        if (allHotels.stream().anyMatch(hotelAtDatabase -> hotelAtDatabase.equals(hotel))) {
            throw new HotelAlreadyExistsException("The " + hotel + "already exists in database "
                    + dbManager.getClass().getSimpleName());
        }
        return DAOHotel.create(hotel);
    }

    /**
     * TODO Игорю на проверку
     * Kontar Maryna:
     *
     * @param hotel
     * @return
     */
    public boolean deleteHotel(Hotel hotel) {

        return DAOHotel.delete(hotel);
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
     * (или на update(Hotel hotel, параметры отеля))
     * Kontar Maryna:
     *
     * @param hotelAtDatabase
     * @param toUpdateHotel
     * @return
     * @throws HotelIsNotInDatabaseException
     */
    public Hotel updateHotel(Hotel hotelAtDatabase, Hotel toUpdateHotel) throws HotelIsNotInDatabaseException {

        if(allHotels.stream().anyMatch(hotel -> hotel.equals(hotelAtDatabase))){
            throw new HotelIsNotInDatabaseException("The " + hotelAtDatabase + "is not in database "
                    + dbManager.getClass().getSimpleName());
        }
        return DAOHotel.update(toUpdateHotel);
    }

}
