package com.abis.models.actors;

import com.abis.exceptiosn.MissingTokenException;

public abstract class Person implements GetCSVRecord {
    public String firstname;
    public String lastname;
    public String email;

    public Person(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("%s %s", this.firstname, this.lastname.toUpperCase());
    }
    public String getCSVLine(){
        StringBuilder sb = new StringBuilder()
                .append(this.getClass().getSimpleName())
                .append(';')
                .append(this.getFirstname())
                .append(';')
                .append(this.getLastname())
                .append(';')
                .append(this.getEmail());

        return sb.toString();
    }

}
