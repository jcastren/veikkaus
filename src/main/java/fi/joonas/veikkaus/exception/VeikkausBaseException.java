package fi.joonas.veikkaus.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class VeikkausBaseException extends RuntimeException {

    private HttpStatus status = HttpStatus.OK;

    protected VeikkausBaseException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public VeikkausBaseException(String message) {
        super(message);
    }

    public VeikkausBaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
