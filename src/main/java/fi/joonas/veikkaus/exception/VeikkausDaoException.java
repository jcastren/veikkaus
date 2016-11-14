package fi.joonas.veikkaus.exception;

public class VeikkausDaoException extends VeikkausBaseException {
	private static final long serialVersionUID = 2078943253769811797L;

	public VeikkausDaoException(String s) {
		super(s);
	}
	
	public VeikkausDaoException(String s, Throwable cause) {
		super(s, cause);
	}
	
	public VeikkausDaoException(String s, Exception e) {
		this(s, e.getCause());
	}

}
