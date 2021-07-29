package com.hotel.roombookingapi.exception;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;

@RestControllerAdvice
@EnableWebMvc
public class DefaultExceptionHandler {
	
	private static Logger logger=LoggerFactory.getLogger(DefaultExceptionHandler.class);

	 @ExceptionHandler(value = TypeMismatchException.class)
	  public ResponseEntity<ErrorMessage> typeMismatchException(TypeMismatchException ex, HttpServletRequest  request) {
	    ErrorMessage message = new ErrorMessage(
	        new Date(),400,"Bad Request",
	        ex.getMessage(),request.getRequestURI());
	    return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
	  }
	 
	 
	 @ExceptionHandler(value = IncorrectUserException.class)
	  public ResponseEntity<ErrorMessage> incorrectUserException(IncorrectUserException ex, HttpServletRequest  request) {
	    ErrorMessage message = new ErrorMessage(
	        new Date(),400,"Bad Request",
	        ex.getMessage(),request.getRequestURI());
	    return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
	  }
	 
	 
	 @ExceptionHandler(value = ResourceNotAvailableException.class)
	  public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotAvailableException ex, HttpServletRequest  request) {
	    ErrorMessage message = new ErrorMessage(
	        new Date(),409,"Room Full",
	        ex.getMessage(),request.getRequestURI());
	    return new ResponseEntity<ErrorMessage>(message, HttpStatus.CONFLICT);
	  }
	 
	
	 
	 
	 @ExceptionHandler(value = Exception.class)
	  public ResponseEntity<ErrorMessage> genericException(Exception ex, HttpServletRequest request) {
		 logger.error("Some error occured"+ex.getMessage());
	    ErrorMessage message = new ErrorMessage(
	        new Date(),500,"Internal Error",
	        "Some Exception Occured"+ex.getMessage(),request.getRequestURI());
	    return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	  }


}
