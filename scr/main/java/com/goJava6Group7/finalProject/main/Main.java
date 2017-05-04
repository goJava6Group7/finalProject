package com.goJava6Group7.finalProject.main;

import com.goJava6Group7.finalProject.controllers.ProjectController;
import com.goJava6Group7.finalProject.data.dataBase.DataBaseManager;
import com.goJava6Group7.finalProject.data.dataBase.impl.DataBaseManagerFactory;

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

        //2. On this step we already have right dataBaseManager and we can give it to controller
        ProjectController controller = new ProjectController(dataBaseManager);
        //3. Create Fresh session of unregister user
        Session session = new Session();

        //4. Give to our menu controller with configured DB and session object to see difference
        //between guest, user and admin
        Menu menu = new Menu(controller, session);

        //Start our main interaction with client
        menu.runMenu();

    }


}
