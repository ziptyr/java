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
public class CourseService implements ICourseService {
    private List<Course> courses;
    private List<Student> students;

    private String pathRoot = "./data";
    private String pathCoursesTxt = pathRoot + "/courses.txt";
    private String pathStudentsTxt = pathRoot + "/students.txt";

    @Autowired
    CourseFileService courseFileService;

    @PostConstruct
    public void postConstructor() {
        /**
         * Test if files exist get courses and students from them.
         *
         * If file is missing create a new and return empty ArrayList.
         */
        try {
            courses = this.courseFileService.readCoursesFromFile(
                this.pathCoursesTxt);
        } catch (FileNotFoundException e) {
            courses = new ArrayList<Course>();
            createFile(this.pathCoursesTxt);
        } catch (Exception e) {
            System.out.println(e);
            courses = new ArrayList<Course>();
            createFile(this.pathCoursesTxt);
        }

        try {
            students = this.courseFileService.readStudentsFromFile(
                this.pathStudentsTxt);
        } catch (FileNotFoundException e) {
            students = new ArrayList<Student>();
            createFile(this.pathStudentsTxt);
        } catch (Exception e) {
            System.out.println(e);
            students = new ArrayList<Student>();
            createFile(this.pathStudentsTxt);
        }
    }

    private static void createFile(String filePath) {
        /**
         * Create new file into given path.
         */
        File file = new File(filePath);
        try {
            file.createNewFile();
        } catch (IOException r) {
            System.out.println(r);
        } catch (SecurityException s) {
            System.out.println(s);
        }
    }

    @Override
    public List<Student> getStudents() {
        /**
         * Return new ArrayList of students.
         */
        return new ArrayList<>(this.students);
    }

    @Override
    public List<Course> getCourses() {
        /**
         * Return new ArrayList of courses.
         */
        return new ArrayList<>(this.courses);
    }

    @Override
    public Student getStudentById(long studentId) {
        /**
         * Return student with matching id.
         */
        return this.students.stream()
            .filter(student -> student.getId() == studentId)
            .findFirst()
            .orElse(null);
    }

    @Override
    public Course getCourseById(long courseId) {
        /**
         * Return course with matching id.
         */
        return this.courses.stream()
            .filter(course -> course.getId() == courseId)
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<Course> getOnlineCourses() {
        /**
         * Return all courses of type "OnlineCourse".
         */
        return this.courses.stream()
            .filter(course -> course.getClass() == OnlineCourse.class)
            .collect(Collectors.toList());
    }

    @Override
    public List<Course> getCoursesOfStudent(long studentId) {
        /**
         * Return all courses of student that given matches id.
         */
        return this.courses.stream()
            .filter(course -> course.getStudents()
                .contains(this.getStudentById(studentId)))
            .collect(Collectors.toList());
    }

    @Override
    public boolean addStudentToCourse(long sid, long cid) {
        /**
         * Add student matching given id to course matching given id.
         */
        Student student = this.getStudentById(sid);
        Course course = this.getCourseById(cid);

        if (course != null && student != null) {
            return course.addStudent(student);
        } else {
            return false;
        }
    }
}
