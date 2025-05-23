package com.abis.repositories.exceptions;

public class SandwichNotFoundException extends RepositoryException {
    public SandwichNotFoundException(String name) {
        super(String.format("Sandwich named <%s> not found", name));
    }
}
