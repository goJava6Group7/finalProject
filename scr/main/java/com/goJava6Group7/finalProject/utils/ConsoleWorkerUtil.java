package com.goJava6Group7.finalProject.utils;

import com.goJava6Group7.finalProject.exceptions.frontend.CheckinDateException;
import com.goJava6Group7.finalProject.exceptions.frontend.OutOfMenuRangeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * Created by Kontar Maryna on 26.04.2017.
 */
public class ConsoleWorkerUtil {

    private ConsoleWorkerUtil() {
    }

    // ************************************* MARYNA ********************************************

    /**
     * Reads integer from console
     * @return integer
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

    /**
     * Reads positive integer from console
     * @return positive integer
     */
    public static int readPositiveInt() {
        return readIntToMaxNum(Integer.MAX_VALUE);
    }


    /**
     * Reads integer from 1 (inclusive) to <code>maxNumber</code> (inclusive) from console
     * @return integer  from 1 (inclusive) to <code>maxNumber</code> (inclusive)
     */
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
     * Read String from console
     * @return String if String isn't empty
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
     * Prints exception's message about wrong integer reading from console
     * @param number wrong integer
     */
    public static void printReadIntFromConsoleException(int number) {
        System.out.println("You typed wrong number. " +
                "And we want a number from 1 to " + number + ". Please, type again.");
    }

    /**
     * Prints exception's message about wrong integer reading from console
     */
    public static void printReadIntFromConsoleException() {
        System.out.println("You typed wrong number. Please, type again.");
    }

    /**
     * Prints exception's message about wrong string reading from console
     */
    public static void printReadStringFromConsoleException() {
        System.out.println("You typed something wrong. Please, type again.");
    }

    /**
     * Prints a confirmation to delete an entity.
     * @param entityName name of entity
     */
    public static void printConfirmDeleteEntity(String entityName) {
        System.out.println("Are you sure you want delete this " + entityName + "?");
    }

    /**
     * Reads password
     * @return
     */
    public static String readPassword() {
        System.out.println("Please enter your password: ");
        return readStringFromConsole();
    }

    /**
     * Reads login
     * @return
     */
    public static String readLogin() {
        System.out.println("Please enter your login: ");
        return readStringFromConsole();
    }

    /**
     * Confirms something
     * @return true if type Y (confirmation) and false otherwise
     */
    public static boolean confirm() {
        System.out.println("Type Y if you agree, else press any key");
        String confirm = readStringFromConsole();
        return confirm.equalsIgnoreCase("Y");

    }


// ************************************* GUILLAUME ********************************************

    /**
     * Reads <code>String</code> from console
     * @param wordDef specifies a type of data, that should be inserted by users
     *                as a <code>String</code> to console
     * @return text, inserted by the <code>User</code> as a <code>String</code>
     */
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

    /**
     * Checks that <code>Integers</code> inserted by <code>User</code>,
     * used as selection numbers in various User Menus,
     * are within the allowed range of selection for the respective User Menu.
     *
     * @return <code>Integer</code>, if inserted value is withing
     * the allowed range for a specific User Menu
     */
    public static int getMenuInput(int min, int max) {
        int choice = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (choice < min || choice > max) {
            try {
                System.out.print("\nEnter your selection:  \n");
                choice = Integer.parseInt(br.readLine());
                if (choice < min || choice > max) throw new OutOfMenuRangeException();

            } catch (NumberFormatException | OutOfMenuRangeException | IOException e) {
                System.out.println("Invalid selection, please try again");
            }
        }
        return choice;
    }

    /**
     * Reads dates from console
     * @return <code>Date</code>
     */
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

    /**
     * Processes the check-in date inserted by <code>User</code>.
     * Prompts the <code>User</code> to enter the <code>Date</code> in correct format and
     * according to the following logic: <code>Date</code> of check-in can not be in the past.
     *
     * @return check-in <code>Date</code>
     * @see #readDateFromConsole()
     */
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

    /**
     * Processes the check-out date inserted by <code>User</code>.
     * Prompts the <code>User</code> to enter the <code>Date</code> in correct format and
     * according to the following logic: <code>Date</code> of check-out
     * must be after the check-in <code>Date</code>, but not more than 30 days.
     *
     * @param checkin <code>Date</code> of check-in
     * @return check-out <code>Date</code>
     * @see #readDateFromConsole()
     */
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
