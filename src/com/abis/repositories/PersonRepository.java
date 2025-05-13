package com.abis.repositories;

import com.abis.models.actors.Person;

import java.util.List;

public interface PersonRepository {
    Person getPersonByName(String firstName, String lastName);
    List<Person> getAll();
    Person getPersonByEmail(String email);
    void addPerson(Person person);
    void removePerson(Person person);

}
