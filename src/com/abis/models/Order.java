package com.abis.models;

import com.abis.models.actors.Person;
import com.abis.models.sandwiches.Sandwich;

import java.util.ArrayList;
import java.util.List;

public class Order {
    public Person student;
    public String course;
    public List<Sandwich> sandwiches=new ArrayList<>();

}
