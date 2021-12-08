package com.example.employee.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


/**
 * ControllerExceptionHandler allows us to handle exceptions in the entire application, 
 * like a global exception handler
 * @author bsaravanan
 *
 */
@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> globalExceptionHandler(Exception ex, WebRequest request) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),new Date(),ex.getMessage(),request.getDescription(false));
    return new ResponseEntity<Object>(message, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}