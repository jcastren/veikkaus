package fi.joonas.veikkaus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VeikkausBadRequestException extends VeikkausBaseException {

    public VeikkausBadRequestException(Class<?> entityClass, Object id, String message) {
        super(HttpStatus.BAD_REQUEST, "%s %s %s".formatted(entityClass.getSimpleName(), id, message));
    }
}
