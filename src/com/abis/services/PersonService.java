package com.abis.services;

import com.abis.models.actors.Person;
import com.abis.repositories.PersonRepository;
import com.abis.repositories.UnitOfWork;
import com.abis.repositories.exceptions.PersonAlreadyExistsException;
import com.abis.repositories.exceptions.PersonNotFoundException;

import java.util.List;

public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(UnitOfWork uow) {
        this.personRepository = uow.getPersonRepository();
    }

    public void addNewStudent(Person person) throws PersonAlreadyExistsException {
        this.personRepository.addPerson(person);
    }

    public Person getPersonByEmail(String mail) throws PersonNotFoundException {
        return this.personRepository.getPersonByEmail(mail);
    }

    public Person getPersonByName(String firstName, String lastName) throws PersonNotFoundException {
        return this.personRepository.getPersonByName(firstName, lastName);
    }

    public List<Person> getAll() {
        return this.personRepository.getAll();
    }
}
