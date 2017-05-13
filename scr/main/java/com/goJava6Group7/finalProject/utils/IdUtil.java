package com.goJava6Group7.finalProject.utils;

import com.goJava6Group7.finalProject.data.dataBase.DataBaseManager;
import com.goJava6Group7.finalProject.entities.Hotel;
import com.goJava6Group7.finalProject.entities.Reservation;
import com.goJava6Group7.finalProject.entities.Room;
import com.goJava6Group7.finalProject.entities.User;
import com.goJava6Group7.finalProject.exceptions.backend.BackendException;

import java.util.Comparator;

import java.util.UUID;


public final class IdUtil {
    private static long userId;
    private static long roomId;
    private static long hotelId;
    private static long reservationId;
    private static boolean isInstanced = false;

    private IdUtil () {}

    public static String IdGenerator(){
        String uniqueID = UUID.randomUUID().toString();
        return uniqueID;
    }
    private IdUtil(long userId, long roomId, long hotelId, long reservationId) {
        this.userId = userId;
        this.roomId = roomId;
        this.hotelId = hotelId;
        this.reservationId = reservationId;
        this.isInstanced = true;
    }

    public static IdUtil getInstance(DataBaseManager dataBaseManager){
        if (!isInstanced){
            long maxRoomID = 0;
            if (dataBaseManager.getDaoRoom().getAll().size() > 0){
                maxRoomID = dataBaseManager.getDaoRoom().getAll().stream().max(Comparator.comparing(Room::getId)).get().getId();
            }
            long maxUserId = 0;
            if (dataBaseManager.getDaoUser().getAll().size()> 0){
                maxUserId = dataBaseManager.getDaoUser().getAll().stream().max(Comparator.comparing(User::getId)).get().getId();
            }
            long maxHotelId = 0;
            if (dataBaseManager.getDaoHotel().getAll().size() > 0){
                maxHotelId = dataBaseManager.getDaoHotel().getAll().stream().max(Comparator.comparing(Hotel::getId)).get().getId();
            }
            long maxReservationId = 0;
            if (dataBaseManager.getDaoReservation().getAll().size() > 0){
                maxReservationId = dataBaseManager.getDaoReservation().getAll().stream().max(Comparator.comparing(Reservation::getId)).get().getId();
            }
            return new IdUtil(maxUserId, maxRoomID, maxHotelId, maxReservationId);
        } else {
            System.err.println("You have created instance IdUtil.class already");
            return null;
        }

    }

    public static long getUserId() {
        try {
            if(!isInstanced) throw new BackendException("You need get instance IdUtil.class");
        } catch (BackendException e){
            e.printStackTrace();
        }
        return ++userId;
    }

    public static long getRoomId() {
        try {
            if(!isInstanced) throw new BackendException("You need get instance IdUtil.class");
        } catch (BackendException e){
            e.printStackTrace();
        }
        return ++roomId;
    }

    public static long getHotelId() {
        try {
            if(!isInstanced) throw new BackendException("You need get instance IdUtil.class");
        } catch (BackendException e){
            e.printStackTrace();
        }
        return ++hotelId;
    }

    public static long getReservationId() {
        try {
            if(!isInstanced) throw new BackendException("You need get instance IdUtil.class");
        } catch (BackendException e){
            e.printStackTrace();
        }
        return ++reservationId;
    }

}
