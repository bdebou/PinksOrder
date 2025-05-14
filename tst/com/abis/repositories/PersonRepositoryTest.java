package com.abis.repositories;

import com.abis.models.actors.Person;
import com.abis.services.PersonService;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PersonRepositoryTest {
    private static PersonService personService;

    @BeforeAll
    public static void init() {
        personService = new PersonService(new UnitOfWork());
    }

    @Test
    public void getAllPersonHasMoreThanOnePerson() {
        List<Person> lstPerson = personService.getAll();
        Assert.isNonEmpty(lstPerson);
        Assert.valueIsAtLeast(lstPerson.size(), 1);
    }

//    Person getPersonByName(String firstName, String lastName);
//    Person getPersonByEmail(String email);
//    void addPerson(Person person);
//    void removePerson(Person person);
}
