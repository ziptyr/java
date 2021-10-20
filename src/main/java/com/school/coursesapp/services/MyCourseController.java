package com.school.coursesapp.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    private String pathRoot = "./data";
    private String pathCoursesTxt = pathRoot + "/courses.txt";
    private String pathStudentsTxt = pathRoot + "/students.txt";

    @Autowired
    CourseFileService courseFileService;

    @PostConstruct
    public void postConstructor() {
        try {
            courses = this.courseFileService.readCoursesFromFile(
                this.pathCoursesTxt);
        } catch (FileNotFoundException e) {
            // error should go into log
            System.out.println(e);
            courses = new ArrayList<Course>();

            // try to create new file
            // i would like to make it into private method, but i'm
            // sticking to the interface design
            File file = new File(this.pathCoursesTxt);
            try {
                file.createNewFile();
            } catch (IOException r) {
                System.out.println(r);
            } catch (SecurityException s) {
                System.out.println(s);
            }
        }

        try {
            students = this.courseFileService.readStudentsFromFile(
                this.pathStudentsTxt);
        } catch (FileNotFoundException e) {
            // error should go into log
            System.out.println(e);
            students = new ArrayList<Student>();

            // try to create new file
            // i would like to make it into private method, but i'm
            // sticking to the interface design
            File file = new File(this.pathStudentsTxt);
            try {
                file.createNewFile();
            } catch (IOException r) {
                System.out.println(r);
            } catch (SecurityException s) {
                System.out.println(s);
            }
        }
    }

    @Override
    public List<Student> getStudents() {
        return new ArrayList<>(this.students);
    }

    @Override
    public List<Course> getCourses() {
        return new ArrayList<>(this.courses);
    }

    @Override
    public Student getStudentById(long studentId) {
        return this.students.stream().filter(student ->
            student.getId() == studentId).findFirst().orElse(null);
    }

    @Override
    public Course getCourseById(long courseId) {
        return this.courses.stream().filter(course ->
            course.getId() == courseId).findFirst().orElse(null);
    }

    @Override
    public List<Course> getOnlineCourses() {
        return this.courses.stream().filter(course ->
            course.getClass() == OnlineCourse.class)
            .collect(Collectors.toList());
    }

    @Override
    public List<Course> getCoursesOfStudent(long studentId) {
        return this.courses.stream().filter(course ->
            course.getStudents().stream().anyMatch(student ->
                student.getId() == studentId)).collect(Collectors.toList());
    }

    @Override
    public boolean addStudentToCourse(long sid, long cid) {
        Student student = this.getStudentById(sid);
        Course course = this.getCourseById(cid);

        if (course != null && student != null) {
            return course.addStudent(student);
        } else {
            return false;
        }
    }
}
