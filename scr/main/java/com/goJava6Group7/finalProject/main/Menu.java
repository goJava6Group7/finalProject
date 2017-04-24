package com.goJava6Group7.finalProject.main;

import com.goJava6Group7.finalProject.controllers.ProjectController;
import com.goJava6Group7.finalProject.entities.Hotel;
import com.goJava6Group7.finalProject.entities.Room;
import com.goJava6Group7.finalProject.entities.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by Igor on 13.04.2017.
 */
public class Menu {
    private ProjectController controller;
    private Session session;
    private BufferedReader br = null;

    public Menu(ProjectController controller, Session session) {

    }

    //Enter point to Menu
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

                case 2:  //TODO Kontar: СДЕЛАТЬ ЭТО ВСЕ ОТДЕЛЬНЫМ МЕТОДОМ

                    boolean b = true;
                    while (b) {
                        try {
                            System.out.println("Please enter the hotel name.");
                            String hotelName = readStringFromConsole();
                            Hotel hotel = controller.findHotelByHotelName(hotelName);
                            System.out.println("There is this hotel in our database!\n" +  hotel);

                            boolean c = true;
                            while (c) {
                                //TODO выдать новый список: зарезервировать комнату, вернуться к выбору гостиницы, вернуться в главное меню
                                System.out.println(
                                        "Choose what you want to do and write the appropriate number: \n" +
                                                "1. Reserve room \n" +
                                                "2. Back to the hotel search . \n" +
                                                "3. Back to main menu. \n"
                                );
                                try {
                                    input = readIntFromConsole();
                                } catch (Exception e) {
                                    printReadIntFromConsoleException(3);
                                    continue;
                                }


                                switch (input) {
                                    case 1:
                                        if (true) { //session.isGuest()
                                            //TODO login or create account
                                            continue;
                                        }
                                        //TODO reserve room
                                        break;

                                    case 2:
                                        //TODO вернуться к while (b). Как сделать это, не объявляя дополнительных переменных (с)?
                                        c = false;
                                        break;

                                    case 3:
                                        //TODO вернуться в главное меню while (true) Как сделать это, не объявляя дополнительных переменных (b,с)?
                                        c = false;
                                        b = false;
                                        break;

                                    default: {
                                        System.out.println("You typed wrong number: " + input + ". " +
                                                "And we want a number from 1 to 3. Please, type again.");
                                        continue;
                                    }
                                }
                            }

                        } catch (Exception e) {
                            //catch IOException from readStringFromConsole() and NoSuchElementException from findHotelByHotelName(hotelName)
                            System.out.println("There is no such hotel in our database.");
                            continue;
                        }
                    }
                    break;


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
                    ;
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



    private int readIntFromConsole() throws IOException {

        br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine(); //throw IOException
        return Integer.parseInt(str);//throw NumberFormatException
//        Scanner scanner = new Scanner(System.in);
//        return scanner.nextInt();
    }

    private String readStringFromConsole() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine(); //throw IOException
    }

    private void printReadIntFromConsoleException(int number) {
        System.out.println("You typed something strange. " +
                "And we want a number from 1 to " + number + ". Please, type again.");
    }

}
