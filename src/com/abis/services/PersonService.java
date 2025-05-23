package com.abis.services;

import com.abis.models.actors.Person;
import com.abis.repositories.PersonRepository;
import com.abis.repositories.UnitOfWork;
import com.abis.repositories.exceptions.PersonAlreadyExistsException;
import com.abis.repositories.exceptions.PersonNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PersonService {
    private static final Logger log = LoggerFactory.getLogger(PersonService.class);
    private final PersonRepository personRepository;

    public PersonService(UnitOfWork uow) {
        this.personRepository = uow.getPersonRepository();
    }

    public void addNewStudent(Person person) throws PersonAlreadyExistsException {
        log.debug("In addNewStudent(Person person)");
        this.personRepository.addPerson(person);
    }

    public Person getPersonByEmail(String mail) throws PersonNotFoundException {
        log.debug("In getPersonByEmail(String mail)");
        return this.personRepository.getPersonByEmail(mail);
    }

    public Person getPersonByName(String firstName, String lastName) throws PersonNotFoundException {
        log.debug("In getPersonByName(String firstName, String lastName)");
        return this.personRepository.getPersonByName(firstName, lastName);
    }

    public List<Person> getAll() {
        log.debug("In getAll()");
        return this.personRepository.getAll();
    }
}
