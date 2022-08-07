package com.example.rqchallenge.exceptionhandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.TooManyRequests;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.rqchallenge.base.response.ErrorResponse;
import com.example.rqchallenge.constants.EmployeeConstants;

@ControllerAdvice
public class EmployeesExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(EmployeesExceptionHandler.class);

	@ExceptionHandler(TooManyRequests.class)
	protected ResponseEntity<Object> handleTooManyRequestErrorHandler(RuntimeException ex, WebRequest request) {
		log.error(EmployeeConstants.ERROR_MSG_TOO_MANY_REQUEST, ex);
		List<String> details = new ArrayList<>();
		details.add(EmployeeConstants.ERROR_MSG_TOO_MANY_REQUEST);
		ErrorResponse error = new ErrorResponse(HttpStatus.TOO_MANY_REQUESTS.name(), details);
		return new ResponseEntity<>(error, HttpStatus.TOO_MANY_REQUESTS);
	}

	@ExceptionHandler(IOException.class)
	protected ResponseEntity<Object> handleIOException(RuntimeException ex, WebRequest request) {
		log.error(EmployeeConstants.ERROR_MSG_IO_EXCEPTION, ex);
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(EmployeeConstants.ERROR_MSG_IO_EXCEPTION, details);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(InternalServerError.class)
	protected ResponseEntity<Object> handleInternalServerError(RuntimeException ex, WebRequest request) {
		log.error(EmployeeConstants.ERROR_MSG_INTERNAL_SERVER, ex);
		List<String> details = new ArrayList<>();
		details.add(EmployeeConstants.ERROR_MSG_INTERNAL_SERVER);
		details.add(ex.getLocalizedMessage());// extra details
		ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), details);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		log.error(EmployeeConstants.ERROR_MSG_INTERNAL_SERVER, ex);
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Internal Server Error", details);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
