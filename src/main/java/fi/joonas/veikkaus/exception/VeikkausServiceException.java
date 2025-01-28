package fi.joonas.veikkaus.exception;

public class VeikkausServiceException extends VeikkausBaseException {
    private static final long serialVersionUID = -824534980842564829L;

    public VeikkausServiceException(String s) {
        super(s);
    }

    public VeikkausServiceException(String s, Throwable cause) {
        super(s, cause);
    }

    public VeikkausServiceException(String s, Exception e) {
        this(s, e.getCause());
    }
}
