package com.goJava6Group7.finalProject.utils;

import java.util.UUID;

/**
 * Created by Igor on 13.04.2017.
 */
public final class IdUtil {

    private IdUtil () {}

    public static String IdGenerator(){
        String uniqueID = UUID.randomUUID().toString();
        return uniqueID;
    }
}
