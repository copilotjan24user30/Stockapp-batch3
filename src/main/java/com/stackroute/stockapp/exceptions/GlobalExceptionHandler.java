package com.stackroute.stockapp.exceptions;

/*
 * Use @ControllerAdvice annotation to handle the exceptions globally
 * Use @ExceptionHandler annotation to handle the exceptions
 * StockNotFoundException, StockAlreadyExistsException
 */

 
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StockNotFoundException.class)
    public ResponseEntity<String> handleStockNotFoundException(StockNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StockAlreadyExistsException.class)
    public ResponseEntity<String> handleStockAlreadyExistsException(StockAlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    //handle EmailIdAlreadyExistsException
    @ExceptionHandler(EmailIdAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailIdAlreadyExistsException(EmailIdAlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
    

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}

 