package com.abis.exceptiosn;

import com.abis.repositories.exceptions.RepositoryException;

public class MissingTokenException extends PinkysOrderException {
    public MissingTokenException(String message) {
        super(String.format("Missing token for <%s>", message));
    }
}
