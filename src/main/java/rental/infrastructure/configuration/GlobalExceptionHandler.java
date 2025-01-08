package rental.infrastructure.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import rental.model.exception.AbstractNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AbstractNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleAbstractNotFoundException(
            AbstractNotFoundException exception, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(exception));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse(ex.getClass().getSimpleName(),
                        "An unexpected error occurred"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(ex));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalStateException(
            IllegalStateException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ExceptionResponse(ex));
    }
}