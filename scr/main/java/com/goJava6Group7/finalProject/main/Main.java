package scr.main.java.com.goJava6Group7.finalProject.main;

import scr.main.java.com.goJava6Group7.finalProject.data.dataBase.DataBaseManager;
import scr.main.java.com.goJava6Group7.finalProject.data.dataBase.impl.DataBaseManagerFactory;


/**
 * Created by Igor on 13.04.2017.
 */
public class Main {
    private static DataBaseManager dataBaseManager;

    public static void main(String[] args) {
        // according to application.properties file we init DatabaseManager
        dataBaseManager = DataBaseManagerFactory.getInstance();
        // read file (in our case "XML_Database") and load to memory, after that we can work with DatabaseManager like with real database
        dataBaseManager.initDB();
    }
}
