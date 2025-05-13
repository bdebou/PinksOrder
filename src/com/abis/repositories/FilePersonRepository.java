package com.abis.repositories;

import com.abis.models.actors.Instructor;
import com.abis.models.actors.Person;
import com.abis.models.actors.Student;
import com.abis.repositories.exceptions.PersonNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class FilePersonRepository implements PersonRepository {
    Person instructor = new Instructor("Sandy", "Schillebeeckx", "JAVA Advanced SE");
    Person student1 = new Student("Raghunath", "Singh", "TCS");
    Person student2 = new Student("Bruno", "Deboubers", "ING");
    Person student3 = new Student("Hans", "Decat", "Contraste");
    Person student4 = new Student("Tushar", "Pandey", "Cognizant");

    private List<Person> persons = new ArrayList<>();

    public FilePersonRepository() {
        persons.add(instructor);
        persons.add(student1);
        persons.add(student2);
        persons.add(student3);
        persons.add(student4);
    }

    @Override
    public Person getPersonByName(String firstName, String lastName) throws PersonNotFoundException {

        return this.persons.stream()
                .filter(person1 -> person1.getFirstname().equalsIgnoreCase(firstName) && person1.getLastname().equalsIgnoreCase(lastName))
                .findFirst().orElseThrow(() -> new PersonNotFoundException("Person not found with firstname"));


    }



    public List<Person> getAll() {
        return this.persons;
    }

    @Override
    public Person getPersonByEmail(String email) throws PersonNotFoundException {
        return this.persons.stream()
                .filter(p1 -> p1.getEmail().equalsIgnoreCase(email))
                .findFirst().orElseThrow(() -> new PersonNotFoundException("Person not found with Email"));
    }

    @Override
    public void addPerson(Person person) {

    }

    @Override
    public void removePerson(Person person) {

    }

}
