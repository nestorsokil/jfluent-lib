package com.jfluent.exception;

/**
 * Created by nestorsokil on 17.03.2017.
 */
public class OperationFailureException extends RuntimeException {
    public OperationFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
