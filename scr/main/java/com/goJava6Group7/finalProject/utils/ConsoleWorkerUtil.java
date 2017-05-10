package com.goJava6Group7.finalProject.utils;

import com.goJava6Group7.finalProject.entities.Hotel;
import com.goJava6Group7.finalProject.entities.Room;
import com.goJava6Group7.finalProject.exceptions.frontend.CheckinDateException;
import com.goJava6Group7.finalProject.exceptions.frontend.OutOfMenuRangeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Kontar Maryna on 26.04.2017.
 */
public class ConsoleWorkerUtil {

    private ConsoleWorkerUtil() {
    }
//*****************************MARYNA***************************************

    /**
     * Kontar Maryna:
     * Method read integer from console
     *
     * @return int entered from the console
     */
    public static int readIntFromConsole() {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                String str = br.readLine(); //throw IOException
                if (!str.isEmpty()) {
                     return Integer.parseInt(str);//throw NumberFormatException
//                    break;
                } else {
                    System.out.println("You don't type anything. Try again.");
                }
            } catch (Exception e) {
                printReadIntFromConsoleException();
            }
        }
//        return number;
    }

    public static int readPositiveInt() {
        return readIntToMaxNum(Integer.MAX_VALUE);
    }

    //TODO У Гийома есть такая функция getMenuInput (с другой сигнатурой),
    // но я для себя переделала и использую не только для считывания номера пункта меню
    public static int readIntToMaxNum(int maxNumber) {
        int readInt;
        while (true) {
            readInt = readIntFromConsole();
            if (readInt > 0 && readInt <= maxNumber)
                break;
            else printReadIntFromConsoleException(maxNumber);
        }
        return readInt;
    }

    /**
     * Kontar Maryna:
     * Method read String from console
     *
     * @return String entered from the console if String isn't empty
     */
    public static String readStringFromConsole() {
//        String str;
        while (true) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String str = br.readLine();  //throw IOException
                if (!str.isEmpty()) return str;
                else {
                    System.out.println("You don't type anything. Try again.");
                }
            } catch (IOException e) {
                printReadStringFromConsoleException();
            }
        }
//        return str;
    }

    /**
     * Kontar Maryna:
     * Method prints exception's message about wrong read integer from console
     *
     * @param number
     */
    public static void printReadIntFromConsoleException(int number) {
        System.out.println("You typed wrong number. " +
                "And we want a number from 1 to " + number + ". Please, type again.");
    }

    public static void printReadIntFromConsoleException() {
        System.out.println("You typed wrong number. Please, type again.");
    }

    public static void printReadStringFromConsoleException() {
        System.out.println("You typed something wrong. Please, type again.");
    }

    public static void printConfirmChangeDB() {
        System.out.println("Are you sure you want to change database? " +
                "If you do, we will restart the system");
    }

    public static void printConfirmDeleteEntity(String entityName) {
        System.out.println("Are you sure you want delete this " + entityName + "?");
    }

    /**
     * Kontar Maryna:
     *
     * @return
     */
    public static String readPassword() {
        System.out.println("Please enter your password: ");
        return readStringFromConsole();
    }

    /**
     * Kontar Maryna:
     *
     * @return
     */
    public static String readLogin() {
        System.out.println("Please enter your login: ");
        return readStringFromConsole();
    }

    /**
     * TODO
     * Kontar Maryna:
     * <p>
     * The method confirm something
     *
     * @return true if type Y and false otherwise
     */
    public static boolean confirm() {
        System.out.println("Type Y if you agree, else press any key");
        String confirm = readStringFromConsole();
        return confirm.equalsIgnoreCase("Y");

    }


// ************************************* GUILLAUME ********************************************


    public static String readNameFromConsole(String wordDef) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String text = "";

        System.out.println("Please enter the " + wordDef);

        while (true) {
            try {
                text = br.readLine();
                break;
            } catch (IOException e) {
                System.out.println("Incorrect input. Please enter the correct " + wordDef);
                continue;
            }
        }
        return text;
    }

    public static int getMenuInput(int min, int max) {
        int choice = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (choice < min || choice > max) {
            try {
                System.out.print("\nEnter your selection");
                choice = Integer.parseInt(br.readLine());
                if (choice < min || choice > max) throw new OutOfMenuRangeException();

            } catch (NumberFormatException | OutOfMenuRangeException | IOException e) {
                System.out.println("Invalid selection, please try again");
            }
        }
        return choice;
    }

    public static LocalDate readDateFromConsole() {
        LocalDate date1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        while (true) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                date1 = LocalDate.parse(br.readLine(), formatter); //throw IOException
                break;
            } catch (Exception e) {
                System.out.println("Please enter the right date format: d/MM/yyyy, for instance 20/01/2000 or 1/01/2000");
                continue;
            }
        }
        return date1;
    }

    public static LocalDate getCheckinDate() {
        LocalDate checkin;

        while (true) {
            try {
                System.out.println("Please enter your desired check-in date");
                checkin = readDateFromConsole();
                if (checkin.isBefore(LocalDate.now())) throw new CheckinDateException();
                break;
            } catch (CheckinDateException e) {
                System.out.println("The check-in date cannot be in the past. Please enter a correct date");
                continue;
            }
        }
        return checkin;
    }


    public static LocalDate getCheckoutDate(LocalDate checkin) {
        LocalDate checkout;
        LocalDate maxcheckout;

        maxcheckout = checkin.plusDays(30);

        while (true) {
            try {
                System.out.println("Please enter your desired check-out date");
                checkout = readDateFromConsole();
                if (checkout.isBefore(checkin) || checkout.isAfter(maxcheckout) || checkout.isEqual(checkin))
                    throw new CheckinDateException();
                break;
            } catch (CheckinDateException e) {
                System.out.println("The check-out must be after your check in date, and cannot be more than 30 days" +
                        " after your check-in date. \nYour check-in date is: " + checkin + " and your maximum check-out" +
                        "date is: " + maxcheckout + ". Please enter a correct date");
                continue;
            }
        }
        return checkout;
    }

}
