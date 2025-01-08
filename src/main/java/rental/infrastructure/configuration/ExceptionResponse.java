package rental.infrastructure.configuration;

public class ExceptionResponse {

    private final String type;
    private final String message;

    public ExceptionResponse(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public ExceptionResponse(Exception ex) {
        this(ex.getClass().getSimpleName(), ex.getMessage());
    }

    public String getType() {
        return this.type;
    }

    public String getMessage() {
        return this.message;
    }
}