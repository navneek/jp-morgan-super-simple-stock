package com.jpm.stock.exception;

/**
 * Exception thrown specifically by the Stock Service during execution of methods within the stock service application
 */
public class StockServiceException extends Exception {

    public StockServiceException(String message) {
        super(message);
    }

    public StockServiceException() {
        super();
    }
}
