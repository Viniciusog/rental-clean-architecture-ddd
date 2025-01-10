package rental.model.exception;

public class InvalidEmailException extends IllegalArgumentException {

    public InvalidEmailException(String address, Throwable cause) {
        super("Invalid email address: " + address, cause);
    }

    public InvalidEmailException(String address) {
        super("Invalid email address: " + address, null);
    }
}