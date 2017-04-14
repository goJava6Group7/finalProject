package com.goJava6Group7.finalProject.data.dataBase.impl;

import com.goJava6Group7.finalProject.data.dataBase.DataBaseManager;
import com.goJava6Group7.finalProject.exceptions.backend.BackendException;

/**
 * Created by Igor on 14.04.2017.
 */
public final class DataBaseManagerFactory {

    private DataBaseManagerFactory(){}

    public enum DataBaseManagerType {XML, BINARY}

    public static DataBaseManager getDataBaseManager(DataBaseManagerType dataBaseManagerType) throws BackendException {
        switch (dataBaseManagerType) {
            case XML:
                return new DataBaseManagerXml();
            case BINARY:
                return new DataBaseManagerBinary();
            default:
                throw new BackendException("There is no dbManager for type: " + dataBaseManagerType);
        }
    }
}
