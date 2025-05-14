package com.abis.services;

import com.abis.models.actors.Person;
import com.abis.repositories.UnitOfWork;
import com.abis.repositories.exceptions.PersonAlreadyExistsException;
import com.abis.repositories.exceptions.PersonNotFoundException;

import java.util.List;

public class PersonService {
    private final UnitOfWork uow;

    public PersonService(UnitOfWork uow) {
        this.uow = uow;
    }

    public void addNewStudent(Person person) throws PersonAlreadyExistsException {
        this.uow.getPersonRepository().addPerson(person);
    }

    public Person getPersonByEmail(String mail) throws PersonNotFoundException {
        return this.uow.getPersonRepository().getPersonByEmail(mail);
    }

    public Person getPersonByName(String firstName, String lastName) throws PersonNotFoundException {
        return this.uow.getPersonRepository().getPersonByName(firstName, lastName);
    }

    public List<Person> getAll() {
        return this.uow.getPersonRepository().getAll();
    }
}
