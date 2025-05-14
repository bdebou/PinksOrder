package com.abis.models.sandwiches;

public abstract class Normal extends Sandwich {
    private boolean salad = false;

    public Normal(String frName, String nlName, Double price) {
        super(frName, nlName, price);
    }

    public boolean isSalad() {
        return salad;
    }

    public void setSalad(boolean salad) {
        this.salad = salad;
    }

    @Override
    public String getCSVLine() {
        StringBuilder sb = new StringBuilder()
                .append(this.getClass().getSimpleName())
                .append(';')
                .append(this.getNameFR())
                .append(';')
                //no descriptionFR
                .append(';')
                .append(this.getNameNL())
                .append(';')
                //no description NL
                .append(';')
                .append(this.getPrice());

        return sb.toString();
    }

    @Override
    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString())
                .append('/')
                .append(this.isSalad() ? "met sla" : "zonder sla");
        return sb.toString();
    }
}
