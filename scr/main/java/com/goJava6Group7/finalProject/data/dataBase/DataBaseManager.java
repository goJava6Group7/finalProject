package com.goJava6Group7.finalProject.data.dataBase;

import com.goJava6Group7.finalProject.data.dao.impl.DaoHotel;
import com.goJava6Group7.finalProject.data.dao.impl.DaoReservation;
import com.goJava6Group7.finalProject.data.dao.impl.DaoRoom;
import com.goJava6Group7.finalProject.data.dao.impl.DaoUser;

/**
 * Created by Igor on 13.04.2017.
 */
public interface DataBaseManager {

    DaoHotel getDaoHotel();

    DaoReservation getDaoReservation();

    DaoRoom getDaoRoom();

    DaoUser getDaoUser();

    boolean initDB();

    boolean updateDatabase();

}
