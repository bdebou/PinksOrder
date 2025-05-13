package com.abis.services;

import com.abis.models.actors.Person;
import com.abis.repositories.UnitOfWork;
import com.abis.repositories.exceptions.PersonNotFoundException;

public class PersonService {
    private final UnitOfWork uow;

    public PersonService(UnitOfWork uow) {
        this.uow = uow;
    }

    public Person getPersonByEmail(String mail) throws PersonNotFoundException {
        return this.uow.getPersonRepository().getPersonByEmail(mail);
    }

    public Person getPersonByName(String firstName, String lastName) throws PersonNotFoundException {
        return this.uow.getPersonRepository().getPersonByName(firstName, lastName);
    }
}
