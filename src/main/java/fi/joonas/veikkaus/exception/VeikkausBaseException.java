package fi.joonas.veikkaus.exception;

public abstract class VeikkausBaseException extends Exception {
    private static final long serialVersionUID = 6226784588127450781L;

    public VeikkausBaseException(String s) {
        super(s);
    }

    public VeikkausBaseException(String s, Throwable cause) {
        super(s, cause);
    }

    public VeikkausBaseException(String s, Exception e) {
        super(s, e.getCause());
    }

}
