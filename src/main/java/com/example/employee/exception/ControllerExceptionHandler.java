package com.example.employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.example.employee.response.EmployeeResponse;


/**
 * ControllerExceptionHandler allows us to handle exceptions in the entire application, 
 * like a global exception handler
 * @author bsaravanan
 *
 */
@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public EmployeeResponse globalExceptionHandler(Exception ex, WebRequest request) {
		EmployeeResponse response = new EmployeeResponse();
		response.setMessage(ex.getMessage());
		response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return response;
	}
}