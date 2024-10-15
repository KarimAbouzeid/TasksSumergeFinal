package org.example.services.impl;

import org.example.Course;
import org.example.CourseRecommender;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Arrays;
import java.util.List;

public class CourseRecommenderImpl1 implements CourseRecommender {
    @Override
    @Qualifier("CourseRecommenderImpl1")
    public List<Course> recommendedCourses() {
        Course c1 = new Course(1, "ADSA", "ADSA Course", 4);
        Course c2 = new Course(2, "Scalable", "Scalable Course", 5);
        Course[] arr = {c1, c2};
        return Arrays.asList(arr);
    }
}
