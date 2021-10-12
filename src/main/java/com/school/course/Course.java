package com.school.course;

import java.util.ArrayList;
import java.util.List;

import com.school.student.Student;

public abstract class Course {
    private static long courseCounter = 0;
    private long id;
    private String name;
    private String teacherName;
    private List<Student> students = new ArrayList<>();

    public Course(String name, String teacherName) {
        this.id = courseCounter++;
        this.name = name;
        this.teacherName = teacherName;
    }

    public boolean addStudent(Student student) {
        this.students.add(student);
        return true;
    }

    public void removeStudent(Student student) {
        this.students.remove(student);
    }

    public List<Student> getStudents() {
        return new ArrayList<Student>(this.students);
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacherName() {
        return this.teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
