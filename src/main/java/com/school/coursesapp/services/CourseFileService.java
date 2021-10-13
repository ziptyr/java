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
    public List<Student> readStudentsFromFile(
        String filePath
    ) throws FileNotFoundException {
        List<Student> students = new ArrayList<>();
        File file = new File(filePath);
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
    public List<Course> readCoursesFromFile(
        String filePath
    ) throws FileNotFoundException {
        List<Course> courses = new ArrayList<>();
        File file = new File(filePath);
        Scanner sc = new Scanner(file);
        String[] line;

        while (sc.hasNextLine()) {
            line = sc.nextLine().split("--");
            if (line[3].equals("online")) {
                courses.add(new OnlineCourse(line[0], line[1], line[2]));
            } else {
                courses.add(new LocalCourse(line[0], line[1], line[2]));
            }
        }

        sc.close();
        return courses;
    }
    
    /*public boolean addStudent(Student student, Course course, String path) {
        boolean hasSpace;
        File file = new File(path);
        file.setWritable(true);
        FileWriter fileWriter;


        hasSpace = course.addStudent(student);

        if (hasSpace) {
            try {
                fileWriter = new FileWriter(file, true);
                fileWriter.write(str);
                fileWriter.close();
            } catch (IOException e) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }*/
}
