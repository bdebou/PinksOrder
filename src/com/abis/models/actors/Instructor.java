package com.abis.models.actors;

import com.abis.exceptiosn.MissingTokenException;

public class Instructor extends CanOrderSandwich {
    String courseName;

    public Instructor(String firstname, String lastname, String email, String courseName) {
        super(firstname, lastname, email);
        this.courseName = courseName;
    }

    @Override
    public String getCSVFormat() {
        StringBuilder sb = new StringBuilder()
                .append(super.getCSVFormat())
                .append(';')
                .append(this.getCourseName());

        return sb.toString();
    }

    public static Instructor parseFromCSV(String csvLine) throws MissingTokenException {
        String[] items = csvLine.split(";");
        if (items.length != 5) throw new MissingTokenException(csvLine);

        return new Instructor(items[1], items[2], items[3], items[4]);
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
