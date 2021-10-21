package com.school.coursesapp.services;

import java.util.List;

import com.school.course.Course;
import com.school.student.Student;

public interface ICourseService {
    List<Student> getStudents();
    List<Course> getCourses();

    Student getStudentById(long studentId);
    Course getCourseById(long courseId);

    List<Course> getOnlineCourses();
    List<Course> getCoursesOfStudent(long studentId);
    boolean addStudentToCourse(long studentId, long courseId);
}
