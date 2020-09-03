package org.itstep.postgres;

public class DaoException extends Exception {
	/**
	 * 
	 */
	private String exceptionText;
	private static final long serialVersionUID = 889506074886115211L;
	public DaoException() {}
	public DaoException(Throwable cause) {
		super(cause);
	}
	public DaoException(String exceptionText) {	
			  this.exceptionText = exceptionText;
		   }
	public String getExceptionText() {
		return exceptionText;
	}
}