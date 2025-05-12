package com.abis.models.sandwiches;

import com.abis.models.enums.BreadType;

public class Fish extends Normal {
    public Fish(String name, Double price, BreadType kind, boolean salad) {
        super(name, price, kind, salad);
    }
}
