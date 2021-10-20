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

    private String pathRoot = "./data";
    private String pathCourses = pathRoot + "/courses.txt";
    private String pathStudents = pathRoot + "/students.txt";

    @Autowired
    CourseFileService courseFileService;

    @PostConstruct
    public void postConstructor() {
        try {
            courses = this.courseFileService.readCoursesFromFile(
                this.pathCourses);
            students = this.courseFileService.readStudentsFromFile(
                this.pathStudents);
        } catch (FileNotFoundException e) {
            System.out.println(e);
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

        //if (course != null && student != null) {
        //    return this.courseFileService.addStudent(student, course);
        //} else {
        //    return false;
        //}

        if (course != null && student != null) {
            return course.addStudent(student);
        } else {
            return false;
        }
    }
}
