package com.school.student;

public class Student {
    static private long studentCounter = 0;
    private long id;
    private String firstName;
    private String lastName;

    public Student(String firstName, String lastName) {
        this.id = Student.studentCounter++;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName;
    }
}
