package com.goJava6Group7.finalProject.main;

import com.goJava6Group7.finalProject.controllers.ProjectController;
import com.goJava6Group7.finalProject.entities.Hotel;
import com.goJava6Group7.finalProject.entities.Room;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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

    public Menu(ProjectController controller, Session session){

    }

    //Enter point to Menu
    public void mainMenu(){

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

            Integer input;
            String str = null;
            try {
                br = new BufferedReader(new InputStreamReader(System.in));
                str = br.readLine(); //throw IOException
                input = Integer.parseInt(str);//throw NumberFormatException
//                Scanner br = new Scanner(System.in);
//                input = br.nextInt();
//                str = input.toString();

            } catch (Exception e) {
                System.out.println("You typed something strange: " + str + ". " +
                        "And we want a number from 1 to 7. Please, type again.");
                continue;

            }

            switch (input) {
                case 1:
                    ;
                    break;

                case 2:
                    boolean b = true;
                    while (b)
                    try {
                        //TODO функция ввода с клавиатуры (название отеля)
                        Hotel hotel = controller.findHotelByHotelName("Miraton");
                        System.out.println(hotel);// информация об отеле со списком комнат (отдельное поле в классе Hotel)
                        b = false;
                        //TODO выдать новый список: вернуться назад, зарезервировать комнату
                    } catch (NoSuchElementException e){
                        System.out.println("There isn't such hotel in our base.");
                    }
                    break;


                case 3:
                    Scanner scanner3 = new Scanner(System.in);

                    while (true){
                        try {
                            System.out.println("Please enter the city name");
                            String cityName = scanner3.next();
                            List<Hotel> cityHotels = controller.findHotelByCityName(cityName);
                            for(Hotel cityHotel : cityHotels) {
                                System.out.println(cityHotel);
                            }
                            break;
                        } catch (NoSuchElementException e){
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
                    for(Room room : rooms) {
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

}
