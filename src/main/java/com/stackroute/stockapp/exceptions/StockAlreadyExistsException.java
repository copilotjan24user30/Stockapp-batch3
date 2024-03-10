package com.stackroute.stockapp.exceptions;

public class StockAlreadyExistsException extends Exception{
    public StockAlreadyExistsException(String message) {
        super(message);
    }

}
