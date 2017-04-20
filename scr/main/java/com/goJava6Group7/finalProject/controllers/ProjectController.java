package com.goJava6Group7.finalProject.controllers;

import com.goJava6Group7.finalProject.data.dao.impl.DaoHotel;
import com.goJava6Group7.finalProject.data.dataBase.DataBaseManager;
import com.goJava6Group7.finalProject.entities.Hotel;
import com.goJava6Group7.finalProject.entities.Reservation;
import com.goJava6Group7.finalProject.entities.Room;
import com.goJava6Group7.finalProject.entities.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
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
     * Kontar Maryna: there are two variants:
     * first return Optional<Hotel>
     * second return List<Hotel>  (Can be a network of hotels with the same name in different cities)
     * @param hotelName
     * @return
     */
    public List<Hotel> findHotelByHotelName(String hotelName) {
        return allHotels.stream()
                .filter((Hotel hotel) -> hotel.getHotelName() == hotelName)
//                 .findFirst();
                .collect(Collectors.toList());
    }

    public Hotel findHotelByCityName(String cityName) {
        throw new UnsupportedOperationException();
    }

    public Room findRoomInHotel(Room room, Hotel hotel) {
        throw new UnsupportedOperationException();
    }


}
