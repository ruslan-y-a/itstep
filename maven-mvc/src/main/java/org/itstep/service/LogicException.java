package org.itstep.service;

public class LogicException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String exceptionText;
	
	public LogicException(String message, Throwable cause) {		
		super(cause);
		this.exceptionText=message;				
	}

	public LogicException(Throwable cause) {		
		super(cause);					
	}

	public String getExceptionText() {
		return exceptionText;
	}
   		
}
