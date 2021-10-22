package com.school.coursesapp.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.school.course.Course;
import com.school.course.LocalCourse;
import com.school.course.OnlineCourse;
import com.school.student.Student;

import org.springframework.stereotype.Service;

@Service
public class CourseFileService implements ICourseFileService {

    @Override
    public List<Student> readStudentsFromFile(String filePath)
        throws FileNotFoundException
    {
        /**
         * Read file from given path into List<Students>.
         *
         * Data delimiter: " ".
         */
        File file = new File(filePath);
        List<Student> students = new ArrayList<>();
        Scanner sc = new Scanner(file);
        String[] line;

        while (sc.hasNextLine()) {
            line = sc.nextLine().split(" ");
            students.add(new Student(line[0], line[1]));
        }

        sc.close();
        return students;
    }

    @Override
    public List<Course> readCoursesFromFile(String filePath)
        throws FileNotFoundException
    {
        /**
         * Read file from given path into List<Courses>.
         *
         * Data delimiter: "--".
         *
         * If data has atleast 3 elements and the last value is "online"
         * the data is made into OnlineCourse. Otherwise into
         * LocalCourse.
         */
        File file = new File(filePath);
        List<Course> courses = new ArrayList<>();
        Scanner sc = new Scanner(file);
        String[] line;

        while (sc.hasNextLine()) {
            line = sc.nextLine().split("--");
            if (line.length >= 3 && line[line.length - 1].equals("online")) {
                courses.add(new OnlineCourse(line[0], line[1], line[2]));
            } else {
                courses.add(new LocalCourse(line[0], line[1], line[2]));
            }
        }

        sc.close();
        return courses;
    }
}
