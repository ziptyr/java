package com.school.coursesapp;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.school.course.Course;
import com.school.coursesapp.services.CourseService;
import com.school.student.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoursesController {

    @Autowired
    CourseService courseService;

    @GetMapping("courses")
    public List<Course> getAllCourses() {
        return this.courseService.getCourses();
    }

    @GetMapping("students")
    public List<Student> getAllStudents() {
        return this.courseService.getStudents();
    }

    @GetMapping("onlinecourses")
    public String getOnlineCourses() {
        return this.courseService.getOnlineCourses()
            .stream()
            .map(course -> "<p>" + course.getName() + "</p>\n")
            .collect(Collectors.joining());
    }

    @GetMapping("students/{id}")
    public String getStudentById(@PathVariable long id) {
        Student student = this.courseService.getStudentById(id);
        String responseMessage = "";

        if (student != null) {
            responseMessage = "<h3>" + student + "</h3>"
                + this.courseService.getCoursesOfStudent(id)
                    .stream()
                    .map(course -> "\n" + course.getName() + "<br />")
                    .collect(Collectors.joining());
        }

        return responseMessage;
    }

    @GetMapping("courses/{id}")
    public String getCourseById(@PathVariable long id) {
        Course course = this.courseService.getCourseById(id);
        String responseMessage = "";

        if (course != null) {
            responseMessage = "<h3>" + course.getName() + "</h3>"
            + course.getStudents()
                .stream()
                .map(student -> "\n" + student.toString() + "<br />")
                .collect(Collectors.joining());
        }

        return responseMessage;
    }

    @PostMapping("add")
    public String addStudentToCourse(
        @RequestBody Map<String, String> ids
    ) {

        long cid, sid;

        cid = Long.parseLong(ids.get("cid"));
        sid = Long.parseLong(ids.get("sid"));

        if (this.courseService.addStudentToCourse(sid, cid)) {
            return "Student added.";
        } else {
            return "Failed. Course might be full.";
        }
    }
}
