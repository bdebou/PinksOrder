package com.abis.repositories;

import com.abis.models.actors.Instructor;
import com.abis.models.actors.Person;
import com.abis.models.actors.Student;

public class PersonRepository extends CommonRepository{
    Person instructor = new Instructor("Sandy","Schillebeeckx","JAVA Advanced SE");
    Person student1 = new Student("Raghunath", "Singh", "TCS");
    Person student2 = new Student("Bruno", "Deboubers", "ING");
    Person student3 = new Student("Hans", "Decat", "Contraste");
    Person student4 = new Student("Tushar", "Pandey", "Cognizant");



}
