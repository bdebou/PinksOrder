package com.abis.models.actors;

public class Student extends CanOrderSandwich {
    String companyName;

    public Student(String firstname, String lastname, String email, String companyName) {
        super(firstname, lastname, email);
        this.companyName = companyName;
    }

    public Student(String firstname, String lastname, String companyName) {
        super(firstname, lastname);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
