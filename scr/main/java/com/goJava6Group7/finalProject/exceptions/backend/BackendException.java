package scr.main.java.com.goJava6Group7.finalProject.exceptions.backend;

/**
 * Created by Igor on 13.04.2017.
 */
public class BackendException extends Exception {
    public BackendException(String message) {
        super(message);
    }

    public BackendException(String message, Throwable cause) {
        super(message, cause);
    }
}
