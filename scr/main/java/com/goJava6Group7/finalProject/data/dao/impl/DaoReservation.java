package com.goJava6Group7.finalProject.data.dao.impl;

import com.goJava6Group7.finalProject.data.dao.Dao;
import com.goJava6Group7.finalProject.data.dataBase.impl.DataBaseManagerFactory;
import com.goJava6Group7.finalProject.entities.Reservation;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Created by Igor on 14.04.2017.
 */
public class DaoReservation implements Dao<Reservation> {
    private List<Reservation> reservations;

    public DaoReservation(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    protected boolean deleteReservationsByHotelId(List<Long> idList) {
        if (idList == null) {
            return false;
        }
        Iterator<Reservation> iterator = reservations.iterator();
        while (iterator.hasNext()) {
            for (Long id : idList) {
                if (iterator.next().getId() == id) {
                    iterator.remove();
                }
            }
        }
        return true;
    }

    @Override
    public Reservation create(Reservation reservation) {
        this.reservations.add(reservation);
        return reservation;
    }

    @Override
    public boolean delete(Reservation reservation) {
        Optional<Reservation> optional = reservations.stream().filter(i -> i.equals(reservation)).findFirst();
        if (optional.isPresent()) {
            reservations.remove(reservation);
            return true;
        }
        return false;
    }

    @Override
    public Reservation update(Reservation reservation) {
        Optional<Reservation> optional = reservations.stream().filter(i -> i.equals(reservation)).findFirst();
        if (optional.isPresent()) {
            reservations.remove(optional.get());
            reservations.add(reservation);
            return reservation;
        }
        return null;
    }

    @Override
    public Reservation get(Reservation reservation) {
        Optional<Reservation> optional = reservations.stream().filter(i -> i.equals(reservation)).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public List<Reservation> getAll() {
        return this.reservations;
    }
}
