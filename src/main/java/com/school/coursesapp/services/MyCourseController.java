package com.school.coursesapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.school.course.Course;
import com.school.course.LocalCourse;
import com.school.course.OnlineCourse;
import com.school.student.Student;

import org.springframework.stereotype.Service;

@Service
public class MyCourseController implements CourseController {
    private List<Course> courses = new ArrayList<>();
    private List<Student> students = new ArrayList<>();

    public MyCourseController() {
        Student pekka = new Student("Pekka", "Pekkala");
        Student minna = new Student("Minna", "Minnala");
        this.students.add(pekka);
        this.students.add(minna);

        Course html = new LocalCourse("Html Fundamentals", "Aapeli Aatamo", "2BC41");
        Course java = new OnlineCourse("Java-ohjelmointi", "Aapeli Aatamo", "www.moodle.oulu.fi");
        Course framework = new LocalCourse("Web Framework", "Kalle Koistinen", "21C4B1A");
        Course cloud = new OnlineCourse("Cloud Services", "Eero Entinen", "oamk.fi/moodle");
        html.addStudent(pekka);
        java.addStudent(minna);
        framework.addStudent(pekka);
        framework.addStudent(minna);
        //cloud.addStudent(pekka);
        this.courses.add(html);
        this.courses.add(java);
        this.courses.add(framework);
        this.courses.add(cloud);
    }

    @Override
    public boolean addStudentToCourse(long studentId, long courseId) {
        return false;
    }

    @Override
    public Course getCourseById(long courseId) {
        return this.courses.stream().filter(course ->
            course.getId() == courseId).findFirst().orElse(null);
    }

    @Override
    public List<Course> getCourses() {
        return new ArrayList<>(this.courses);
    }

    @Override
    public List<Course> getCoursesOfStudent(long studentId) {
        return this.courses.stream().filter(course -> 
            course.getStudents().stream().anyMatch(student -> 
                student.getId() == studentId))
            .collect(Collectors.toList());
    }

    @Override
    public Student getStudentById(long studentId) {
        return this.students.stream().filter(student ->
        student.getId() == studentId).findFirst().orElse(null);
    }

    @Override
    public List<Student> getStudents() {
        return new ArrayList<>(this.students);
    }

    public List<Course> getOnlineCourses() {
        return this.courses.stream().filter(course -> 
            course.getClass() == OnlineCourse.class)
            .collect(Collectors.toList());
    }
}
