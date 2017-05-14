package com.goJava6Group7.finalProject.data.dao.impl;

import com.goJava6Group7.finalProject.data.dao.Dao;
import com.goJava6Group7.finalProject.data.dataBase.impl.DataBaseManagerFactory;
import com.goJava6Group7.finalProject.entities.Reservation;
import com.goJava6Group7.finalProject.entities.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoRoom implements Dao<Room> {
    private List<Room> rooms;

    public DaoRoom(List<Room> rooms) {
        this.rooms = rooms;
    }

    protected boolean deleteRoomsByHotelId(List<Long> idList) {
        if (idList == null) return false;

        for (Long id : idList) {
            rooms.removeIf(room -> room.getId() == id);
        }
        return true;
    }



    @Override
    public Room create(Room room) {
        this.rooms.add(room);
        DataBaseManagerFactory.getDataBaseManager().updateDatabase();

        return room;
    }

    /**
     * The method delete room with it's reservations from list of all rooms
     * and delete room's reservations from list of all reservations
     *
     * @param room
     * @return
     */
    @Override
    public boolean delete(Room room) {
        Optional<Room> optional = rooms.stream().filter(i -> i.equals(room)).findFirst();
        if (!optional.isPresent()) return false;

        List<Long> idList = new ArrayList<>();
        List<Reservation> reservations = optional.get().getBookings();//все резервации комнаты
        if (reservations != null) {
            reservations.forEach(reservation -> idList.add(reservation.getId()));
            DataBaseManagerFactory.getDataBaseManager().getDaoReservation()
                    .deleteReservationsByRoomId(idList);
        }
        return rooms.remove(optional.get());
    }

    @Override
    public Room update(Room room) {
        Optional<Room> optional = rooms.stream().filter(i -> i.equals(room)).findFirst();
        if (optional.isPresent()) {
            rooms.remove(optional.get());
            rooms.add(room);
            return room;
        }
        return null;
    }

    @Override
    public Room get(Room room) {
        Optional<Room> optional = rooms.stream().filter(i -> i.equals(room)).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public List<Room> getAll() {
        return rooms;
    }

    @Override
    public String toString() {
        return "DaoRoom{" +
                "rooms=" + rooms +
                '}';
    }
}
