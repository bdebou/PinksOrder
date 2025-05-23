package com.abis.services.exceptions;

import com.abis.models.actors.Person;

public class NotAuthorizedException extends ServiceException {
    public NotAuthorizedException(String message) {
        super(message);
    }
    public NotAuthorizedException(Person person){
        super(String.format("<%s> is not authorized to add a sandwich", person.toString()));
    }
}
