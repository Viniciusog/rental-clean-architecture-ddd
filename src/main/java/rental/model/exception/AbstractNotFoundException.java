package rental.model.exception;

public abstract class AbstractNotFoundException extends RuntimeException {

    public AbstractNotFoundException(String message) {
        super(message);
    }
}