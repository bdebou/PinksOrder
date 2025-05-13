package com.abis.repositories;

import com.abis.models.actors.Person;
import com.abis.repositories.exceptions.PersonNotFoundException;

import java.util.List;

public interface PersonRepository {
    Person getPersonByName(String firstName, String lastName) throws PersonNotFoundException;
    List<Person> getAll();
    Person getPersonByEmail(String email) throws PersonNotFoundException;
    void addPerson(Person person);
    void removePerson(Person person);

}
