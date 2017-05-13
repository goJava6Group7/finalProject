package com.goJava6Group7.finalProject.data.dataBase;

import com.goJava6Group7.finalProject.data.dao.impl.DaoHotel;
import com.goJava6Group7.finalProject.data.dao.impl.DaoReservation;
import com.goJava6Group7.finalProject.data.dao.impl.DaoRoom;
import com.goJava6Group7.finalProject.data.dao.impl.DaoUser;

public interface DataBaseManager {

    DaoHotel getDaoHotel();

    DaoReservation getDaoReservation();

    DaoRoom getDaoRoom();

    DaoUser getDaoUser();

    boolean initDB();

    boolean updateDatabase();

}
