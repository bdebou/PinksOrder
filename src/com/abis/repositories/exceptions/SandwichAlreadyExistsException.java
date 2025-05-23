package com.abis.repositories.exceptions;

public class SandwichAlreadyExistsException extends RepositoryException {
    public SandwichAlreadyExistsException(String message) {
        super(message);
    }

  public SandwichAlreadyExistsException() {
    super();
  }
}
