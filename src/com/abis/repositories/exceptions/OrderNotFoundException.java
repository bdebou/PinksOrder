package com.abis.repositories.exceptions;

public class OrderNotFoundException extends RepositoryException {
    public OrderNotFoundException(String name) {
        super(name);
    }
}
