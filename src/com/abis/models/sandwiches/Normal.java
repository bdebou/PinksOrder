package com.abis.models.sandwiches;

import com.abis.models.enums.BreadType;

public abstract class Normal extends Sandwich {
    private boolean salad = false;

    public Normal(String frName, String nlName, Double price, BreadType kind, boolean salad) {
        super(frName, nlName, price, kind);
        this.salad = salad;
    }

    public boolean isSalad() {
        return salad;
    }

    public void setSalad(boolean salad) {
        this.salad = salad;
    }
}
