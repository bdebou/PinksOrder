package com.abis.models.actors;

public class Student extends CanOrderSandwich {
    String companyName;

    public Student(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
