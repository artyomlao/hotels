package auth.model.exception;

import org.springframework.http.HttpStatus;

public class JwtAuthenticationException extends RuntimeException {

    private final HttpStatus httpStatus;

    public JwtAuthenticationException(final String message, final HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public JwtAuthenticationException(final String message, final HttpStatus httpStatus, final Exception e) {
        super(message, e);
        this.httpStatus = httpStatus;
    }
}
