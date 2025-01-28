package fi.joonas.veikkaus.exception;

public class VeikkausConversionException extends VeikkausBaseException {
    private static final long serialVersionUID = 3099984347684675096L;

    public VeikkausConversionException(String s) {
        super(s);
    }

    public VeikkausConversionException(String s, Throwable cause) {
        super(s, cause);
    }

    public VeikkausConversionException(String s, Exception e) {
        this(s, e.getCause());
    }

}
