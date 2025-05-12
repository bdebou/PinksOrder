package com.abis.models.sandwiches;

import com.abis.models.enums.BreadType;

public class Vegetarian extends HasDescription {
    public Vegetarian(String frName, String nlName, String frDescription, String nlDescription, Double price) {
        super(frName, nlName, frDescription, nlDescription, price);
    }
}
