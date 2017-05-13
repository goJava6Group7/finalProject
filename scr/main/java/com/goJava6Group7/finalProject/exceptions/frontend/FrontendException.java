package com.goJava6Group7.finalProject.exceptions.frontend;

import com.goJava6Group7.finalProject.entities.Room;


public class FrontendException extends Exception {

    public FrontendException(String message) {
        super(message);
    }

    public FrontendException(String message, Throwable cause) {
        super(message, cause);
    }

}