package fi.joonas.veikkaus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VeikkausNotFoundException extends VeikkausBaseException {

    public VeikkausNotFoundException(Class<?> entityClass, Object id) {
        super(HttpStatus.NOT_FOUND, entityClass.getSimpleName() + " with id " + id + " not found");
    }
}
