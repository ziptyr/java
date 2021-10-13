package com.school.coursesapp.services;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.school.course.Course;
import com.school.course.OnlineCourse;
import com.school.student.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyCourseController implements CourseController {
    private List<Course> courses;
    private List<Student> students;

    @Autowired
    CourseFileService courseFileService;

    @PostConstruct
    public void postConstructor() {
        String dataPath = "./data";
        try {
            courses = this.courseFileService.readCoursesFromFile(
                dataPath + "/courses.txt");
            students = this.courseFileService.readStudentsFromFile(
                dataPath + "/students.txt");
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    @Override
    public boolean addStudentToCourse(long studentId, long courseId) {
        Course course = this.getCourseById(courseId);
        Student student = this.getStudentById(studentId);

        if (course != null && student != null) {
            return course.addStudent(student);
        } else {
            return false;
        }
    }

    @Override
    public Course getCourseById(long courseId) {
        return this.courses.stream().filter(course ->
            course.getId() == courseId).findFirst().orElse(null);
    }

    @Override
    public List<Course> getCourses() {
        return new ArrayList<>(this.courses);
    }

    @Override
    public List<Course> getCoursesOfStudent(long studentId) {
        return this.courses.stream().filter(course -> 
            course.getStudents().stream().anyMatch(student -> 
                student.getId() == studentId)).collect(Collectors.toList());
    }

    @Override
    public Student getStudentById(long studentId) {
        return this.students.stream().filter(student ->
            student.getId() == studentId).findFirst().orElse(null);
    }

    @Override
    public List<Student> getStudents() {
        return new ArrayList<>(this.students);
    }

    public List<Course> getOnlineCourses() {
        return this.courses.stream().filter(course -> 
            course.getClass() == OnlineCourse.class)
            .collect(Collectors.toList());
    }
}
