package org.example.services.impl;

import jakarta.annotation.PostConstruct;
import org.example.Course;
import org.example.CourseRecommenderImpl2;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Primary
public class CourseRecommenderImpl3 extends CourseRecommenderImpl2 {

    @PostConstruct
    public void init() {
        System.out.println("CourseRecommenderImpl3 bean created");
    }

    @Override
    public List<Course> recommendedCourses(){
        Course c1 = new Course(1, "PE", "PE Course", 6);
        Course c2 = new Course(2, "ART", "ART Course", 4);
        Course[] arr = {c1, c2};
        return Arrays.asList(arr);
    }
}
