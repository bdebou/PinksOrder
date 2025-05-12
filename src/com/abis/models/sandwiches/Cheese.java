package com.abis.models.sandwiches;

import com.abis.models.enums.BreadType;

public class Cheese extends Normal {
    public Cheese(String frName, String nlName, Double price, BreadType kind, boolean salad) {
        super(frName, nlName, price, kind, salad);
    }
}
