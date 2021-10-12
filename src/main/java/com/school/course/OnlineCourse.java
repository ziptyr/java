package com.school.course;

public class OnlineCourse extends Course {
    private String courseUrl;

    public OnlineCourse(String name, String teacherName, String courseUrl) {
        super(name, teacherName);
        this.courseUrl = courseUrl;
    }

    public String getCourseUrl() {
        return this.courseUrl;
    }

    public void setCourseUrl(String courseUrl) {
        this.courseUrl = courseUrl;
    }

    @Override
    public String toString() {
        return this.getName() + " - "
            + this.getTeacherName() + " - "
            + this.getCourseUrl();
    }
}
