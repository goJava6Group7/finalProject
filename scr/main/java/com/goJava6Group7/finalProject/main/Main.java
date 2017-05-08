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

        // How we can add new rooms to hotel !!!!!
        /*Hotel hotel = new Hotel("President","Kiev",4);
        dataBaseManager.getDaoHotel().create(hotel);
        Room r1 = new Room(1,100, RoomClass.Apartment);
        Room r2 = new Room(2,200, RoomClass.Apartment);
        Room r3 = new Room(3,400, RoomClass.President);
        List<Room> list = new ArrayList<>();
        list.add(r1);
        list.add(r2);
        list.add(r3);
        dataBaseManager.getDaoHotel().get(hotel).setRooms(list); // add to hotel rooms
*/
        /// in frontEnd this method we can call like projectController.getDbManager.updateDatabase(); before that we need to add get to dbManager field
        dataBaseManager.updateDatabase();

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
