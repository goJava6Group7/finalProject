package com.goJava6Group7.finalProject.controllers;

import com.goJava6Group7.finalProject.data.dataBase.DataBaseManager;
import com.goJava6Group7.finalProject.entities.Hotel;
import com.goJava6Group7.finalProject.entities.Reservation;
import com.goJava6Group7.finalProject.entities.Room;
import com.goJava6Group7.finalProject.entities.User;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Created by Igor on 13.04.2017.
 */
public class ProjectController {

    private DataBaseManager dbManager;
    private List<Hotel> allHotels = dbManager.getDaoHotel().getAll();
    private List<User> allUsers = dbManager.getDaoUser().getAll();


    public ProjectController(DataBaseManager dbManager) {
        this.dbManager = dbManager;
    }


    /**
     * Kontar Maryna: метод сделан пока без проверок на существование такого же аккаунта
     * @param name
     * @param login
     * @param password
     * @return
     */
    public User createAccount(String name, String login, String password) {
        User user = new User(name, login, password);
        if(
        allUsers.stream()
                .filter((User o) -> o.getName() == name || o.getLogin() == login)
                .findFirst()
                .isPresent())
// TODO не доделано

        {
            dbManager.getDaoUser().create(user);
        }
        return user;
    }

    public Reservation reserveRoom(User user, Room room, Hotel hotel, Date dataOfArrival, Date dateOfDeparture) {
        Reservation reservation = dbManager.getDaoReservation()
                .create(new Reservation(user, room, hotel, dataOfArrival, dateOfDeparture));

        //TODO как сохранить теперь это в БД, и как добавить в список заказа user? Функция должна быть с входными параметрами
        return reservation;
    }

    public boolean cancelRoomReservation() {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO Игорю на проверку
     * Kontar Maryna:
     * method can throw NoSuchElementException("No value present")
     * @param hotelName
     * @return
     */
    public Hotel findHotelByHotelName(String hotelName) throws NoSuchElementException{
        return allHotels.stream()
                .filter((Hotel hotel) -> hotel.getHotelName().equals( hotelName))
                 .findFirst().get();
    }

    public List <Hotel> findHotelByCityName(String cityName) throws NoSuchElementException {
        // access database
        //DaoHotel hotelDAO = new DaoHotel();
        //List <Hotel> cityHotels = hotelDAO.getAll();
        return allHotels.stream()
                .filter((Hotel hotel) -> hotel.getHotelCity().equals(cityName))
                .collect(Collectors.toList());
    }

    public List <Room> findRoomInHotel(Hotel hotel) {

        return hotel.getHotelRooms();

    }
}
