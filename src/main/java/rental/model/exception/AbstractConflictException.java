package rental.model.exception;

public class AbstractConflictException extends RuntimeException {

    public AbstractConflictException(String message) {
        super(message);
    }
}