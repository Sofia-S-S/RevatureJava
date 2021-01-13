package com.sverbank.exeption;

//6

public class BusinessException extends Exception {
	
	// Source - Generate constructors from superclass: empty() and (String)

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusinessException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BusinessException(final String arg0) { //change to final
		super(arg0);
		// TODO Auto-generated constructor stub
	} 
//now we can throw this exception	
	
	
	

}
