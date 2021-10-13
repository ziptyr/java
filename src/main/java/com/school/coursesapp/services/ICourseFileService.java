package com.school.coursesapp.services;

import java.io.FileNotFoundException;
import java.util.List;

import com.school.course.Course;
import com.school.student.Student;

public interface ICourseFileService {
    List<Student> readStudentsFromFile(
        String filePath) throws FileNotFoundException;
    List<Course> readCoursesFromFile(
        String filePath) throws FileNotFoundException;
}
