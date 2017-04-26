package com.goJava6Group7.finalProject.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

}
