package com.abis.models.sandwiches;

import com.abis.models.enums.BreadType;

public class Cheese extends Normal {
    public Cheese(String name, Double price, BreadType kind, boolean salad) {
        super(name, price, kind, salad);
    }
}
