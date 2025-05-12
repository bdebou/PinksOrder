package com.abis.models.actors;

public class Instructor extends CanOrderSandwich {
    String courseName;

    public Instructor(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
