package com.goJava6Group7.finalProject.exceptions.frontend;

import com.goJava6Group7.finalProject.entities.Room;

/**
 * Created by guillaume on 4/23/17.
 */
public class NoSuchRoomException extends Exception {

    public NoSuchRoomException(Room room){
        System.err.println("There is already a room with this name in this hotel:");
        System.err.println(room);
    }
}

