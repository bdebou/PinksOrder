package com.abis.repositories;

import com.abis.models.actors.Person;
import com.abis.repositories.exceptions.PersonAlreadyExistsException;
import com.abis.repositories.exceptions.PersonNotFoundException;

import java.util.List;

public interface PersonRepository {
    Person getPersonByName(String firstName, String lastName) throws PersonNotFoundException;

    List<Person> getAll();

    Person getPersonByEmail(String email) throws PersonNotFoundException;

    void addPerson(Person person) throws PersonAlreadyExistsException;

    void removePerson(Person person) throws PersonNotFoundException;

    void removePerson(String email) throws PersonNotFoundException;

    void removePerson(String firstName, String lastName) throws PersonNotFoundException;
}
