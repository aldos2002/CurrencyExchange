package com.epam.exceptions;

/**
 * Created by Almas_Doskozhin
 * on 2/21/2016.
 */
public class BusinessException extends Exception {
    BusinessException() {
        super();
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
