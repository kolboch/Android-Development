package bochynski.karol.bmi.exceptions;

/**
 * Created by Karol on 2017-03-23.
 */

public class InvalidHeightException extends RuntimeException {

    public InvalidHeightException() {
        super();
    }

    public InvalidHeightException(String message) {
        super(message);
    }
}
