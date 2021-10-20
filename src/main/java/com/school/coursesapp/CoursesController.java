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
        return new ResponseEntity<>(
            myCourseController.getCourses(), HttpStatus.OK
        );
    }

    @GetMapping("students")
    public ResponseEntity<List<Student>> getAllStudents() {
        return new ResponseEntity<>(
            myCourseController.getStudents(), HttpStatus.OK
        );
    }

    @GetMapping("onlinecourses")
    public String getOnlineCourses() {
        return myCourseController.getOnlineCourses().stream()
            .map(course -> "<p>" + course.getName() + "</p>\n")
            .collect(Collectors.joining());
    }

    @GetMapping("students/{id}")
    public String getStudentById(@PathVariable long id) {
        Student student = myCourseController.getStudentById(id);
        String response;

        if (student != null) {
            response = "<p>" + myCourseController.getStudentById(id).toString()
            + "</p>";
        } else {
            response = "Student not found";
        }

        return response;
    }

    @GetMapping("courses/{id}")
    public String getCourseById(@PathVariable long id) {
        Course course = myCourseController.getCourseById(id);
        String courseInfo;

        if (course != null) {
            courseInfo = "<h3>" + course.getName() + "</h3>";

            courseInfo += course.getStudents().stream().map(student ->
                "\n<br />" + student.getFirstName() + student.getLastName())
                .collect(Collectors.joining());
        } else {
            courseInfo = "Course not found";
        }

        return courseInfo;
    }

    @PostMapping("add")
    public ResponseEntity<String> addStudentToCourse(
        @RequestBody Map<String, String> ids
    ) {
        long sid, cid;
        boolean success;
        String message = "";

        // ArrayList.size() returns int, this will be an issue if the
        // "school" ever gets more than 2^31 - 1 (~2,15 billion) student
        // or course records
        //  1. i believe this to be extremely unlikely for our "school"
        //  2. ArrayLists themselves can only store int max value of
        //     records
        int courses_size = myCourseController.getCourses().size();
        int students_size = myCourseController.getCourses().size();

        try {
            sid = Long.parseLong(ids.get("sid"));
            cid = Long.parseLong(ids.get("cid"));

            if (sid < 0 || sid > students_size) {
                success = false;
                message = "Student id is invalid";
            } else if (cid < 0 || cid > courses_size) {
                success = false;
                message = "Course id is invalid";
            } else {
                success = myCourseController.addStudentToCourse(sid, cid);
                if (!success) {message = "Local course is full";}
            }
        } catch (NumberFormatException e) {
            System.out.println(e);
            success = false;
            message = "Student or course id could not be parsed into 'long'";
        } catch (Exception e) {
            System.out.println(e);
            success = false;
            message = "Adding failed";
        }

        if (success) {
            return new ResponseEntity<String>("Student added", HttpStatus.OK);
        } else {
            if (message.equals("")) {message = "Adding failed";}

            return new ResponseEntity<String>(
                message, HttpStatus.BAD_REQUEST);
        }
    }
}
