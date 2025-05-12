package com.abis.models.sandwiches;

import com.abis.models.enums.BreadType;

public abstract class Sandwich {
    private String name;
    private Double price;
    private BreadType kind;

    public Sandwich(String name, Double price, BreadType kind) {
        this.name = name;
        this.price = price;
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public BreadType getKind() {
        return kind;
    }

    public void setKind(BreadType kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
