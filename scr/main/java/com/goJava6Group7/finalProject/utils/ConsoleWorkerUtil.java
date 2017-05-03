package com.goJava6Group7.finalProject.utils;

import com.goJava6Group7.finalProject.exceptions.frontend.CheckinDateException;
import com.goJava6Group7.finalProject.exceptions.frontend.OutOfMenuRangeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Created by Kontar Maryna on 26.04.2017.
 */
public class ConsoleWorkerUtil {

    private ConsoleWorkerUtil() {
    }

    /**
     * TODO убрать лишнии комментарии перед сдачей проекта
     * Kontar Maryna:
     * Method read integer from console
     * @return int entered from the console
     * @throws IOException
     */
    public static int readIntFromConsole() throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine(); //throw IOException
        br.close();
        return Integer.parseInt(str);//throw NumberFormatException

//        Scanner scanner = new Scanner(System.in);
//        return scanner.nextInt();
    }

    /**
     * TODO убрать лишнии комментарии перед сдачей проекта
     * Kontar Maryna:
     * Method read String from console
     * @return String entered from the console
     * @throws IOException
     */
    public static String readStringFromConsole() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine(); //throw IOException
        br.close();
        return str;
    }

    /**
     * TODO убрать лишнии комментарии перед сдачей проекта
     * Kontar Maryna:
     * Method print exception message typing wrong int from console
     * @param number
     */
    public static void printReadIntFromConsoleException(int number) {
        System.out.println("You typed something strange. " +
                "And we want a number from 1 to " + number + ". Please, type again.");
    }

    /**
     * Kontar Maryna:
     *
     * @return
     * @throws IOException
     */
    public static String askPassword() throws IOException {
        System.out.println("Please enter your password: ");
        return readStringFromConsole();
    }

    /**
     * Kontar Maryna:
     *
     * @return
     * @throws IOException
     */
    public static String askLogin() throws IOException {
        System.out.println("Please enter your login: ");
        return readStringFromConsole();
    }



    public static String readNameFromConsole(String wordDef){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String text = "";

        System.out.println("Please enter the " + wordDef);

        while(true) {
            try{
                text = br.readLine();
                break;
            }catch (IOException e) {
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

    public static LocalDate readDateFromConsole(){
        LocalDate date1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        while(true){
            try{
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                date1 = LocalDate.parse(br.readLine(),formatter); //throw IOException
                break;
            } catch (Exception e){
                System.out.println("Please enter the right date format: d/MM/yyyy, for instance 20/01/2000 or 1/01/2000");
                continue;
            }
        }
        return date1;
    }

    public static LocalDate getCheckinDate(){
        LocalDate checkin;

        while(true){
            try{
                System.out.println("Please enter your desired check-in date");
                checkin = readDateFromConsole();
                if (checkin.isBefore(LocalDate.now())) throw new CheckinDateException();
                break;
            }catch (CheckinDateException e){
                System.out.println("The check-in date cannot be in the past. Please enter a correct date");
                continue;
            }
        }
        return checkin;
    }


    public static LocalDate getCheckoutDate(LocalDate checkin){
        LocalDate checkout;
        LocalDate maxcheckout;

        maxcheckout = checkin.plusDays(30);

        while(true){
            try{
                System.out.println("Please enter your desired check-out date");
                checkout = readDateFromConsole();
                if (checkout.isBefore(checkin) || checkout.isAfter(maxcheckout) || checkout.isEqual(checkin))
                    throw new CheckinDateException();
                break;
            }catch (CheckinDateException e){
                System.out.println("The check-out must be after your check in date, and cannot be more than 30 days" +
                        " after your check-in date. \nYour check-in date is: " + checkin + " and your maximum check-out" +
                        "date is: " + maxcheckout + ". Please enter a correct date");
                continue;
            }
        }
        return checkout;
    }

}
