package com.abis.models;

import com.abis.models.actors.Person;
import com.abis.models.sandwiches.Sandwich;
import exception.MaxSandwichesReachedException;

import java.util.ArrayList;
import java.util.List;

public class Order {
    public Person orderingPerson;
    public String course;
    public List<Sandwich> sandwiches = new ArrayList<>();

    public Order(String course, Person orderingPerson) {
        this.course = course;
        this.orderingPerson = orderingPerson;
    }

    public Person getOrderingPerson() {
        return orderingPerson;
    }

    public void setOrderingPerson(Person orderingPerson) {
        this.orderingPerson = orderingPerson;
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
        if (sandwiches.size() < 2) {
            sandwiches.add(sandwich);
        } else throw new MaxSandwichesReachedException("Maximum 2 sandwiches per person allowed!");
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderingPerson=" + orderingPerson +
                ", course='" + course + '\'' +
                ", sandwiches=" + sandwiches +
                '}';
    }

    public String formatPrintOrder() {
        StringBuilder stringBuilder = new StringBuilder("");

        for(Sandwich s1 : sandwiches){
            stringBuilder.append(s1.getNameNL())
                    .append("/")
                    .append(s1.getKind())
                    .append(" ");
        }
        String allSandwiches = stringBuilder.toString();

        String formatted = String.format("%1$-15s%2$-15s%3$-20s%4$-50s%5$s", orderingPerson.lastname.toUpperCase(),
                orderingPerson.firstname,course,allSandwiches,sandwiches.size());
        System.out.println(formatted);
 //        sandwiches.forEach(s1 -> stringBuilder.append(s1.getNameNL()).append(" "));
//      sandwiches.forEach(s1 -> stringBuilder.append(s1.getKind()).append(", "));




            return formatted;


    }
}
