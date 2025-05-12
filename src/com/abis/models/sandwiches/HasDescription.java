package com.abis.models.sandwiches;

import com.abis.models.enums.BreadType;

public abstract class HasDescription extends Sandwich {
    private String frDescription;
    private String nlDescription;

    public HasDescription(String frName, String nlName, Double price, BreadType kind, String frDescription, String nlDescription) {
        super(frName, nlName, price, kind);
        this.frDescription = frDescription;
        this.nlDescription = nlDescription;
    }

    public String getNlDescription() {
        return nlDescription;
    }

    public void setNlDescription(String nlDescription) {
        this.nlDescription = nlDescription;
    }

    public String getFrDescription() {
        return frDescription;
    }

    public void setFrDescription(String frDescription) {
        this.frDescription = frDescription;
    }

    @Override
    public String toString() {
        return this.getNameFR() + "(" + this.getFrDescription() + ")";
    }
}
