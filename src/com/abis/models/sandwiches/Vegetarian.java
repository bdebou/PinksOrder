package com.abis.models.sandwiches;

import com.abis.models.enums.BreadType;

public class Vegetarian extends HasDescription{
    public Vegetarian(String name, Double price, BreadType kind, String description) {
        super(name, price, kind, description);
    }
}
