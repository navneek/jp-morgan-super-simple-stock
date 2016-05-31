package com.jpm.stock.exception;

/**
 * Exception to mention the stock not found
 */
public class StockNotFoundException extends Exception {

    public StockNotFoundException(String message) {
        super(message);
    }
}
