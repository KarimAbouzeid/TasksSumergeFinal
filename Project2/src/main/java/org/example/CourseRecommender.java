package org.example;


import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CourseRecommender {
        List<Course> recommendedCourses();

}
