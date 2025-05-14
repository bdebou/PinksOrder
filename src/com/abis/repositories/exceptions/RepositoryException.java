package com.abis.repositories.exceptions;

public class RepositoryException extends Exception {
    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException() {
        super();
    }
}
