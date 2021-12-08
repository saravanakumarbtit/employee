package com.example.employee.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ErrorMessage is a POJO class to form the error message when an exception is thrown
 * @author bsaravanan
 *
 */
@Data
@AllArgsConstructor
public class ErrorMessage {
	  private int statusCode;
	  private Date timestamp;
	  private String message;
	  private String description;		  
}
