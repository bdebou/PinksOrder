package com.abis.services.exceptions;

public class NotAuthorizedException extends ServiceException {
    public NotAuthorizedException(String message) {
        super(message);
    }
}
