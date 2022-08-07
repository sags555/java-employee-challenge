package com.example.rqchallenge.exceptionhandler;

public class EmployeeValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	public EmployeeValidationException() {
		super();
	}

	public EmployeeValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmployeeValidationException(Throwable cause) {
		super(cause);
	}

	public EmployeeValidationException(String errorMessage) {
		super(errorMessage);
	}

}
