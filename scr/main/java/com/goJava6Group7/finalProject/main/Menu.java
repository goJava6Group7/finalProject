package com.goJava6Group7.finalProject.main;

import com.goJava6Group7.finalProject.controllers.ProjectController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
                    ;
                    break;
                case 3:
                    ;
                    break;
                case 4:
                    ;
                    break;
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
