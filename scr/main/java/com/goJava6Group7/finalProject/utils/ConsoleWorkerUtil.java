package com.goJava6Group7.finalProject.utils;

import com.goJava6Group7.finalProject.exceptions.frontend.CheckinDateException;

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

    public static int readIntFromConsole() throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine(); //throw IOException
        return Integer.parseInt(str);//throw NumberFormatException
//        Scanner scanner = new Scanner(System.in);
//        return scanner.nextInt();
    }

    public static String readStringFromConsole() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine(); //throw IOException
    }

    public static void printReadIntFromConsoleException(int number) {
        System.out.println("You typed something strange. " +
                "And we want a number from 1 to " + number + ". Please, type again.");
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
