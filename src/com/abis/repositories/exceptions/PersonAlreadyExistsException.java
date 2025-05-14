package com.abis.repositories.exceptions;

public class PersonAlreadyExistsException extends RepositoryException {
    public PersonAlreadyExistsException(String message) {
        super(message);
    }

    public PersonAlreadyExistsException() {
        super();
    }
}
