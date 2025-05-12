package com.abis.models.sandwiches;

import com.abis.models.enums.BreadType;

public abstract class Sandwich {
    private String nameFR;
    private String nameNL;
    private Double price;
    private BreadType kind;

    public Sandwich(String frName, String nlName, Double price) {
        this.nameFR = frName;
        this.nameNL = nlName;
        this.price = price;
        this.kind = BreadType.WHITE;
    }


    public String getNameNL() {
        return nameNL;
    }

    public void setNameNL(String nameNL) {
        this.nameNL = nameNL;
    }

    public String getNameFR() {
        return nameFR;
    }

    public void setNameFR(String nameFR) {
        this.nameFR = nameFR;
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
        return this.getNameFR();
    }
}
