package scr.main.java.com.goJava6Group7.finalProject.exceptions.frontend;

/**
 * Created by Igor on 13.04.2017.
 */
public class FrontendException extends Exception {

    public FrontendException(String message) {
        super(message);
    }

    public FrontendException(String message, Throwable cause) {
        super(message, cause);
    }
}
