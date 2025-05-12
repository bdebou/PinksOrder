package com.abis.services;

import com.abis.models.actors.OfficeManager;
import com.abis.models.actors.Person;
import com.abis.models.sandwiches.Sandwich;
import com.abis.repositories.UnitOfWork;
import com.abis.repositories.exceptions.SandwichAlreadyExistsException;
import com.abis.services.exceptions.NotAuthorizedException;

public class SandwichService {
    private UnitOfWork uow;

    public SandwichService(UnitOfWork uow) {
        this.uow = uow;
    }

    public void addSandwich(Person person, Sandwich sandwich) throws SandwichAlreadyExistsException, NotAuthorizedException {
        if (this.amIAuthorized(person)) {
            this.uow.getSandwichRepository().addSandwich(sandwich);
        }
    }

    public void removeSandwich(Person person, String nameOfSandwich) throws NotAuthorizedException {
        if (this.amIAuthorized(person)) {
            this.uow.getSandwichRepository().removeSandwich(nameOfSandwich);
        }
    }

    private boolean amIAuthorized(Person person) throws NotAuthorizedException {
        if (!(person instanceof OfficeManager)) {
            throw new NotAuthorizedException(String.format("<%s> is not authorized to add a sandwich", person.toString()));
        }
        return true;
    }
}
