package com.System.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(FieldEmptyException.class)
	public ResponseEntity<String> emptyField(FieldEmptyException e)
	{
		//System.out.println("Handler hit");
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<String> elementnotPresent(NullPointerException e)
	{
		//System.out.println("Handler hit");
		return new ResponseEntity<String>("field is null",HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DuplicateRecordException.class)
	public ResponseEntity<String> emptyField(DuplicateRecordException e)
	{
		//System.out.println("Handler hit");
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<String> emptyField(RecordNotFoundException e)
	{
		//System.out.println("Handler hit");
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<String> emptyField(ConstraintViolationException e)
	{
		//System.out.println("Handler hit");
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DuplicateUsernameException.class)
	public ResponseEntity<String> emptyField(DuplicateUsernameException e)
	{
		//System.out.println("Handler hit");
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	public GlobalExceptionHandler() {
		// TODO Auto-generated constructor stub
	}

}
