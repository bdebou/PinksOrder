package com.abis.models.sandwiches;

import com.abis.models.enums.BreadType;

public abstract class HasDescription extends Sandwich {
    private String description;

    public HasDescription(String name, Double price, BreadType kind, String description) {
        super(name, price, kind);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.getName() + "(" + this.getDescription() + ")";
    }
}
