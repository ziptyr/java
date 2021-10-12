package com.school.coursesapp;

import java.util.List;

import com.school.course.Course;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoursesController {

    //@Autowired
    //MyCourseController myCourseController;

    @GetMapping("courses")
    public ResponseEntity<List<Course>> getAllStudents() {
    //    return new RequestEntity<List>(
    //        myCourseController.getCourses(), HttpStatus.OK
    //    );
        //return new ResponseEntity<>(myCourseController.getCourses(), HttpStatus.OK);
        return null;
    }
}
