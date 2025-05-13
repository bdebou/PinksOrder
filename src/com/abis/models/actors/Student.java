package com.abis.models.actors;

import com.abis.exceptiosn.MissingTokenException;

public class Student extends CanOrderSandwich {
    String companyName;

    public Student(String firstname, String lastname, String email, String companyName) {
        super(firstname, lastname, email);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String getCSVFormat() {
        StringBuilder sb = new StringBuilder()
                .append(super.getCSVFormat())
                .append(';')
                .append(this.getCompanyName());

        return sb.toString();
    }

    public static Student parseFromCSV(String csvLine) throws MissingTokenException {
        String[] items = csvLine.split(";");
        if (items.length != 5) throw new MissingTokenException(csvLine);

        return new Student(items[1], items[2], items[3], items[4]);
    }
}
