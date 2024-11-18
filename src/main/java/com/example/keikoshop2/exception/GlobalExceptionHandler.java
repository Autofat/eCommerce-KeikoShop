package com.example.keikoshop2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

  // Handle ProductNotFoundExeption
  @ExceptionHandler(ProductNotFoundExeption.class)
  public ResponseEntity<String> handleProductNotFound(ProductNotFoundExeption ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);  // Return a 404 status with the error message
  }

  // Catch all other exceptions
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGenericException(Exception ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  // Return 500 status
  }
}
