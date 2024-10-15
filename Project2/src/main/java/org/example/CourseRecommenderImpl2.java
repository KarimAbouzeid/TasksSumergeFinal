package org.example;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;



@Component
public class CourseRecommenderImpl2 implements CourseRecommender {

    public List<Course> recommendedCourses(){
        Course c1 = new Course(1, "Chemistry", "Chem Course", 5);
        Course c2 = new Course(2, "Maths", "Maths Course", 5);
        Course[] arr = {c1, c2};
        return Arrays.asList(arr);
    }

}
