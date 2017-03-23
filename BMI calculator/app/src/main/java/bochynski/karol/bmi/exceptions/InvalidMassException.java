package bochynski.karol.bmi.exceptions;

/**
 * Created by Karol on 2017-03-23.
 */

public class InvalidMassException extends RuntimeException {

    public InvalidMassException() {
        super();
    }

    public InvalidMassException(String message) {
        super(message);
    }
}
