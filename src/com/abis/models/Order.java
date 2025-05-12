package com.abis.models;

import com.abis.models.actors.Person;
import com.abis.models.sandwiches.Sandwich;
import exception.MaxSandwichesReachedException;

import java.util.ArrayList;
import java.util.List;

public class Order {
    public Person student;
    public String course;
    public List<Sandwich> sandwiches=new ArrayList<>();

    public Order(List<Sandwich> sandwiches, String course, Person student) {
        this.sandwiches = sandwiches;
        this.course = course;
        this.student = student;
    }

    public Person getStudent() {
        return student;
    }

    public void setStudent(Person student) {
        this.student = student;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public List<Sandwich> getSandwiches() {
        return sandwiches;
    }

    public void setSandwiches(List<Sandwich> sandwiches) {
        this.sandwiches = sandwiches;
    }

    public void addSandwich(Sandwich sandwich) throws MaxSandwichesReachedException {
        if (sandwiches.size() <= 2){
            sandwiches.add(sandwich);
        }
        else throw new MaxSandwichesReachedException("Maximum 2 sandwiches per person allowed!");
    }
}
