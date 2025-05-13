package com.abis.models.sandwiches;

import com.abis.models.enums.BreadType;

import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Sandwich sandwich = (Sandwich) o;
        return nameFR.equalsIgnoreCase(sandwich.nameFR)
                && nameNL.equalsIgnoreCase(sandwich.nameNL)
                && price.equals(sandwich.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameFR, nameNL, price);
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

    public abstract String getCSVLine();

    @Override
    public String toString() {
        return this.getNameFR();
    }
}
