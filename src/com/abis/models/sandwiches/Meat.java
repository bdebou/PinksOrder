package com.abis.models.sandwiches;

import com.abis.models.enums.BreadType;

public class Meat extends Normal {
    public Meat(String name, Double price, BreadType kind, boolean salad) {
        super(name, price, kind, salad);
    }

}
