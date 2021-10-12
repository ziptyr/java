package com.school.course;

import com.school.student.Student;

public class LocalCourse extends Course {
    private String classroom;
    private int maxStudents = 25;

    public LocalCourse(String name, String teacherName, String classroom) {
        super(name, teacherName);
        this.classroom = classroom;
    }
    
    @Override
    public boolean addStudent(Student student) {
        if (this.getStudents().size() >= this.maxStudents) {
            return false;
        } else {
            return super.addStudent(student);
        }
    }

    public String getClassroom() {
        return this.classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    @Override
    public String toString() {
        return this.getName() + " - "
            + this.getTeacherName() + " - "
            + this.getClassroom();
    }
}
