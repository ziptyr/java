package com.school.coursesapp;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.school.course.Course;
import com.school.coursesapp.services.MyCourseController;
import com.school.student.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoursesController {

    @Autowired
    MyCourseController myCourseController;

    @GetMapping("courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        HttpStatus httpStatus;
        List<Course> courses = this.myCourseController.getCourses();

        if (courses.size() > 0) {httpStatus = HttpStatus.OK;}
        else {httpStatus = HttpStatus.NO_CONTENT;}

        return new ResponseEntity<List<Course>>(courses, httpStatus);
    }

    @GetMapping("students")
    public ResponseEntity<List<Student>> getAllStudents() {
        HttpStatus httpStatus;
        List<Student> students = this.myCourseController.getStudents();

        if (students.size() > 0) {httpStatus = HttpStatus.OK;}
        else {httpStatus = HttpStatus.NO_CONTENT;}

        return new ResponseEntity<List<Student>>(students, httpStatus);
    }

    @GetMapping("onlinecourses")
    public ResponseEntity<String> getOnlineCourses() {
        HttpStatus httpStatus;
        List<Course> courses = this.myCourseController.getOnlineCourses();
        String message = courses.stream()
            .map(course -> "<p>" + course.getName() + "</p>\n")
            .collect(Collectors.joining());

        if (courses.size() == 0) {httpStatus = HttpStatus.BAD_REQUEST;}
        else if (message.equals("")) {httpStatus = HttpStatus.NO_CONTENT;}
        else {httpStatus = HttpStatus.OK;}

        return new ResponseEntity<String>(message, httpStatus);
    }

    @GetMapping("students/{id}")
    public ResponseEntity<String> getStudentById(@PathVariable long id) {
        HttpStatus httpStatus;
        String message;
        Student student = this.myCourseController.getStudentById(id);

        if (student != null) {
            httpStatus = HttpStatus.OK;
            message = "<p>" + this.myCourseController.getStudentById(id)
                .toString() + "</p>";
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
            message = "Student not found";
        }

        return new ResponseEntity<String>(message, httpStatus);
    }

    @GetMapping("courses/{id}")
    public ResponseEntity<String> getCourseById(@PathVariable long id) {
        Course course = this.myCourseController.getCourseById(id);
        HttpStatus httpStatus;
        String message;

        if (course != null) {
            httpStatus = HttpStatus.OK;
            message = "<h3>" + course.getName() + "</h3>";
            message += course.getStudents().stream().map(student ->
                "\n<br />" + student.getFirstName() + student.getLastName())
                .collect(Collectors.joining());
        } else {
            httpStatus = HttpStatus.NOT_FOUND;
            message = "Course not found";
        }

        return new ResponseEntity<String>(message, httpStatus);
    }

    @PostMapping("add")
    public ResponseEntity<String> addStudentToCourse(
        @RequestBody Map<String, String> ids
    ) {

        boolean success;
        HttpStatus httpStatus;
        long cid, sid;
        String message = "";

        try {
            cid = Long.parseLong(ids.get("cid"));
            sid = Long.parseLong(ids.get("sid"));
            success = this.myCourseController.addStudentToCourse(sid, cid);
        } catch (NumberFormatException e) {
            message = "Student or course id could not be parsed into 'long'";
            success = false;
        } catch (Exception e) {
            message = "Adding failed";
            success = false;
        }

        if (success) {
            httpStatus = HttpStatus.ACCEPTED;
            message = "Student added";
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
            if (message.equals("")) {message = "Failed. Course might be full";}
        }

        return new ResponseEntity<String>(message, httpStatus);
    }
}
