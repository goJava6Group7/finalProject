package com.goJava6Group7.finalProject.data.dataBase.impl;

import com.goJava6Group7.finalProject.data.dao.impl.DaoHotel;
import com.goJava6Group7.finalProject.data.dao.impl.DaoReservation;
import com.goJava6Group7.finalProject.data.dao.impl.DaoRoom;
import com.goJava6Group7.finalProject.data.dao.impl.DaoUser;
import com.goJava6Group7.finalProject.data.dataBase.DataBaseManager;

/**
 * Created by Igor on 14.04.2017.
 */
public final class DataBaseManagerXml implements DataBaseManager {

    protected DataBaseManagerXml(){}

    @Override
    public DaoHotel getDaoHotel() {
        throw new UnsupportedOperationException();
    }

    @Override
    public DaoReservation getDaoReservation() {
        throw new UnsupportedOperationException();
    }

    @Override
    public DaoRoom getDaoRoom() {
        throw new UnsupportedOperationException();
    }

    @Override
    public DaoUser getDaoUser() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean initDB() {
        throw new UnsupportedOperationException();
    }
}
