package com.abis.models.sandwiches;

import com.abis.models.enums.BreadType;

public class Vegetarian extends HasDescription{
    public Vegetarian(String frName, String nlName, Double price, BreadType kind, String frDescription, String nlDescription) {
        super(frName, nlName, price, kind, frDescription, nlDescription);
    }
}
