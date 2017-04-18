package com.goJava6Group7.finalProject.main;

import com.goJava6Group7.finalProject.controllers.ProjectController;
import com.goJava6Group7.finalProject.data.dataBase.DataBaseManager;

/**
 * Created by Igor on 13.04.2017.
 */
public class Main {
    private static DataBaseManager dataBaseManager;

    public static void main(String[] args) {
        try {
            //1. Choose what DB we use and maybe some other settings
            dataBaseManager = configDB();
        } catch (Exception e) {
            System.out.println("Catch configDB exception");
        }

        //2. On this step we already have right dataBaseManager and we can give it to controller
        ProjectController controller = new ProjectController(dataBaseManager);
        //3. Create Fresh session of unregister user
        Session session = new Session();

        //4. Give to our menu controller with configured DB and session object to see difference
        //between guest, user and admin
        Menu menu = new Menu(controller, session);

        //Start our main interaction with client
        menu.mainMenu();

    }


    //Start of our app. Have to be some console interaction with client
    //Use here DataBaseManagerFactory depend on client selection
    private static DataBaseManager configDB() {
        throw new UnsupportedOperationException();
    }
}
