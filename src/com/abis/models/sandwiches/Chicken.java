package com.abis.models.sandwiches;

import com.abis.models.enums.BreadType;

public class Chicken extends Normal {
    public Chicken(String name, Double price, BreadType kind, boolean salad) {
        super(name, price, kind, salad);
    }
}
