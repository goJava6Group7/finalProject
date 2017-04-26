package com.goJava6Group7.finalProject.main;

import com.goJava6Group7.finalProject.controllers.ProjectController;
import com.goJava6Group7.finalProject.entities.Hotel;
import com.goJava6Group7.finalProject.entities.Room;
import com.goJava6Group7.finalProject.exceptions.frontend.AccountAlreadyExistException;

import java.io.BufferedReader;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static com.goJava6Group7.finalProject.utils.ConsoleWorkerUtil.*;


/**
 * Created by Igor on 13.04.2017.
 */
public class Menu {
    private ProjectController controller;
    private Session session;

    public Menu(ProjectController controller, Session session) {
        this.controller = controller;
        this.session = session;
    }

    //Enter point to Menu
//    TODO(Answer1) Сделать switch с 7 case. Под каждый case сделать выполнение отдельного приватного метода
    public void mainMenu() {

        System.out.println("Hello! Welcome to our project! ");

        while (true) {

            System.out.println(
                    "Choose what you want to do and write the appropriate number: \n" +
                            "1. Login \n" +
                            "2. Find hotel by hotel name. \n" +
                            "3. Find hotel by city name. \n" +
                            "4. Find room in hotel. \n" +
                            "5. Create account \n" +
                            "6. \n" +
                            "7. Exit\n");

            int input;
            try {
                input = readIntFromConsole();
            } catch (Exception e) {
                printReadIntFromConsoleException(7);
                continue;
            }

            switch (input) {
                case 1:
                    ;
                    break;

                case 2:
                    findHotelByHotelName(); //TODO Kontar:


                case 3:
                    Scanner scanner3 = new Scanner(System.in);

                    while (true) {
                        try {
                            System.out.println("Please enter the city name");
                            String cityName = scanner3.next();
                            List<Hotel> cityHotels = controller.findHotelByCityName(cityName);
                            for (Hotel cityHotel : cityHotels) {
                                System.out.println(cityHotel);
                            }
                            break;
                        } catch (NoSuchElementException e) {
                            System.out.println("There is no such hotel in our database");
                            continue;
                        }
                    }
                    scanner3.close();

                case 4:
                    Scanner scanner4 = new Scanner(System.in);
                    System.out.println("Please enter the hotel name");
                    String hotelName = scanner4.next();
                    Hotel hotelRoom = controller.findHotelByHotelName(hotelName);
                    List<Room> rooms = controller.findRoomsInHotel(hotelRoom);
                    for (Room room : rooms) {
                        System.out.println(room);
                    }
                    scanner4.close();

                case 5:
                    break;
                case 6:
                    ;
                    break;
                case 7:
                    System.out.println(" You are leaving our program :(  Come again!");
                    System.exit(0);
                default: {
                    System.out.println("You typed wrong number: " + input + ". " +
                            "And we want a number from 1 to 7. Please, type again.");
                    continue;
                }

            }
        }
    }


    private void findHotelByHotelName() {

        System.out.println("Please enter the hotel name.");
        try {
            String hotelName = readStringFromConsole();
            Hotel hotel = controller.findHotelByHotelName(hotelName);
            System.out.println("There is this hotel in our database!\n" + hotel);
            IDontKnowWhatToCallFunction();

        } catch (Exception e) {
            //catch IOException from readStringFromConsole() and NoSuchElementException from findHotelByHotelName(hotelName)
            System.out.println("There is no such hotel in our database.");
            findHotelByHotelName();
        }
    }


    private void IDontKnowWhatToCallFunction() {

        //TODO выдать новый список: зарезервировать комнату, вернуться к выбору гостиницы, вернуться в главное меню
        System.out.println(
                "Choose what you want to do and write the appropriate number: \n" +
                        "1. Reserve room \n" +
                        "2. Back to the hotel search . \n" +
                        "3. Back to main menu. \n"
        );

        int input;
        try {
            input = readIntFromConsole();
            switch (input) {
                case 1:
                    if (true) { //session.isGuest()
                        //TODO login or create account
                        IDontKnowWhatToCallFunction();
                    }

//                    reserveRoom();
//                    System.out.println("You room: " + room + "is reserved");

                case 2:
                    findHotelByHotelName();
                case 3:
                    mainMenu();
                default: {
                    System.out.println("You typed wrong number: " + input + ". " +
                            "And we want a number from 1 to 3. Please, type again.");
                    IDontKnowWhatToCallFunction();
                }
            }
        } catch (Exception e) {
            printReadIntFromConsoleException(3);
            IDontKnowWhatToCallFunction();
        }
    }

//    TODO(Answer2)Эти методы можно вынести в отдельный утильный класс ConsoleWorkerUtil

}
