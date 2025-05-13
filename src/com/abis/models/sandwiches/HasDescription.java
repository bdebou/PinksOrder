package com.abis.models.sandwiches;

import com.abis.models.enums.BreadType;

public abstract class HasDescription extends Sandwich {
    private String frDescription;
    private String nlDescription;

    public HasDescription(String frName, String nlName, String frDescription, String nlDescription, Double price) {
        super(frName, nlName, price);
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
    public String getCSVLine() {
        StringBuilder sb = new StringBuilder()
                .append(this.getClass().getSimpleName())
                .append(';')
                .append(this.getNameFR())
                .append(';')
                .append(this.getFrDescription())
                .append(';')
                .append(this.getNameNL())
                .append(';')
                .append((this.getNlDescription()))
                .append(';')
                .append(this.getPrice());

        return sb.toString();
    }

    @Override
    public String toString() {
        return this.getNameFR() + "(" + this.getFrDescription() + ")";
    }
}
