package scr.main.java.com.goJava6Group7.finalProject.data.dataBase.impl;

import scr.main.java.com.goJava6Group7.finalProject.data.dataBase.DataBaseManager;
import scr.main.java.com.goJava6Group7.finalProject.data.dataBase.impl.DataBaseManagerBinary;
import scr.main.java.com.goJava6Group7.finalProject.data.dataBase.impl.DataBaseManagerXml;
import scr.main.java.com.goJava6Group7.finalProject.exceptions.backend.BackendException;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Properties;

/**
 * Created by Igor on 14.04.2017.
 */
public final class DataBaseManagerFactory {
    private static DataBaseManager dataBaseManager;

    public static DataBaseManager getDataBaseManager() {
        return dataBaseManager;
    }

    public static DataBaseManager getInstance(){
        if (Files.exists(FileSystems.getDefault().getPath("application.properties"))) {
            Properties properties = new Properties();
            try {
                properties.load(Files.newInputStream(FileSystems.getDefault().getPath("application.properties")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            switch (properties.getProperty("dbType")){
                case "BINARY": {
                    dataBaseManager = new DataBaseManagerBinary();
                    break;
                }
                case "XML": {
                    dataBaseManager = new DataBaseManagerXml();
                    break;
                }
                default: {
                    System.err.println("Invalid configuration in application.properties file. Application.properties file will have default configuration");
                    createDefaultPropertiesFile();
                    dataBaseManager = new DataBaseManagerXml();
                }
            }
        } else {
            System.err.println("Application.properties file doesn't exist. Application.properties will be created and configured by default");
            createDefaultPropertiesFile();
            dataBaseManager = new DataBaseManagerXml();
        }
        return dataBaseManager;
    }

    private static void createDefaultPropertiesFile(){
        Properties properties = new Properties();
        properties.setProperty("dbType", "XML");
        try {
            properties.store(Files.newOutputStream(FileSystems.getDefault().getPath("application.properties")),null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
