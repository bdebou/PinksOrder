package com.abis.repositories.exceptions;

public class TypeNotImplementedException extends RepositoryException {
    public TypeNotImplementedException(String typeName) {
        super(String.format("Wrong type <%s>", typeName));
    }
}
