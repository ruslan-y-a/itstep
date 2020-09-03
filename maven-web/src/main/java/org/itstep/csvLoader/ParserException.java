package org.itstep.csvLoader;

public class ParserException extends Exception {

	private static final long serialVersionUID = -1688257560365104317L;
	private String exceptionText;
	
	public ParserException(String message, Throwable cause) {		
		super(cause);
		this.exceptionText=message;				
	}
	public ParserException(String message) {				
		this.exceptionText=message;				
	}
	public ParserException(Throwable cause) {		
		super(cause);					
	}

	public String getExceptionText() {
		return exceptionText;
	}
   		
}