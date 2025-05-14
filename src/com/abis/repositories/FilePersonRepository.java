package com.abis.repositories;

import com.abis.exceptiosn.MissingTokenException;
import com.abis.models.actors.Instructor;
import com.abis.models.actors.Person;
import com.abis.models.actors.Student;
import com.abis.models.sandwiches.Sandwich;
import com.abis.repositories.exceptions.PersonAlreadyExistsException;
import com.abis.repositories.exceptions.PersonNotFoundException;
import com.abis.repositories.exceptions.SandwichAlreadyExistsException;
import com.abis.repositories.exceptions.TypeNotImplementedException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FilePersonRepository implements PersonRepository {
    private final List<Person> persons = new ArrayList<>();
    private final File pathPersonsList;

    public FilePersonRepository(File inputFile) {
        this.pathPersonsList = inputFile;
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String csvLine;
            /* escape the header line */
            br.readLine();
            while ((csvLine = br.readLine()) != null) {
                try {
                    /* Type,FirstName;LastName;EMAIL;Company|CourseName */
                    String[] items = csvLine.split(";");
                    if (items.length == 5) {
                        Person person = getPerson(items);
                        this.persons.add(person);
                    } else {
                        throw new MissingTokenException(csvLine);
                    }
                } catch (MissingTokenException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException | TypeNotImplementedException e) {
            System.out.println(e.getMessage());
        }
    }

    private Person getPerson(String[] items) throws TypeNotImplementedException {
        String type = items[0];
        return switch (type.toLowerCase()) {
            case "student" -> new Student(items[1], items[2], items[3], items[4]);
            case "instructor" -> new Instructor(items[1], items[2], items[3], items[4]);
            default -> throw new TypeNotImplementedException(type);
        };
    }

    @Override
    public Person getPersonByName(String firstName, String lastName) throws PersonNotFoundException {

        return this.persons.stream()
                .filter(person1 -> person1.getFirstname().equalsIgnoreCase(firstName) && person1.getLastname().equalsIgnoreCase(lastName))
                .findFirst().orElseThrow(() -> new PersonNotFoundException("Person not found with firstname"));


    }

    private void save() {
        File tmpFile = null;
        boolean bSaved = false;
        try {
            tmpFile = File.createTempFile("persons", null);

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(tmpFile, StandardCharsets.UTF_8))) {
                /* Add the header line */
                bw.write("TType,FirstName;LastName;EMAIL;Company|CourseName");
                bw.write(System.lineSeparator());

                for (Person person : this.persons) {
                    bw.write(person.getCSVLine());
                    bw.write(System.lineSeparator());
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            if (this.pathPersonsList.delete()) {
                bSaved = tmpFile.renameTo(this.pathPersonsList);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (!bSaved && tmpFile != null && tmpFile.exists()) {
                tmpFile.delete();
            }
        }
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
    public void addPerson(Person person) throws PersonAlreadyExistsException {
        if (this.persons.contains(person)) {
            throw new PersonAlreadyExistsException();
        }
        this.persons.add(person);
        this.save();
    }

    @Override
    public void removePerson(Person person) {
        this.persons.remove(person);
        this.save();
    }

    @Override
    public void removePerson(String email) throws PersonNotFoundException {
        Person personToRemove = this.getPersonByEmail(email);
        this.removePerson(personToRemove);
    }

    @Override
    public void removePerson(String firstName, String lastName) throws PersonNotFoundException {
        Person personToRemove = this.getPersonByName(firstName, lastName);
        this.removePerson(personToRemove);
    }

}
