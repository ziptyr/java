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
        return "<p>" + myCourseController.getStudentById(id).toString()
            + "</p>";
    }

    @GetMapping("courses/{id}")
    public String getCourseById(@PathVariable long id) {
        Course course = myCourseController.getCourseById(id);
        String courseInfo = "<h3>" + course.getName() + "</h3>";

        courseInfo += course.getStudents().stream().map(student ->
            "\n<br />" + student.getFirstName() + student.getLastName())
            .collect(Collectors.joining());

        return courseInfo;
    }

    @PostMapping("add")
    public ResponseEntity<String> addStudentToCourse(
        @RequestBody Map<String, String> ids
    ) {
        long sid, cid;
        boolean success;

        sid = Long.parseLong(ids.get("sid"));
        cid = Long.parseLong(ids.get("cid"));
        success = myCourseController.addStudentToCourse(sid, cid);

        if (success) {
            return new ResponseEntity<String>("Student added", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>(
                "Adding failed", HttpStatus.FORBIDDEN);
        }
    }
}
