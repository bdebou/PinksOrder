package com.abis.models.sandwiches;

import com.abis.models.enums.BreadType;

public class Special extends HasDescription{
    public Special(String name, Double price, BreadType kind, String description) {
        super(name, price, kind, description);
    }
}
