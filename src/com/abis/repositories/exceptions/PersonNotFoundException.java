package com.abis.repositories.exceptions;

public class PersonNotFoundException extends RepositoryException {
    public PersonNotFoundException(String name) {
        super(name);
    }
}
