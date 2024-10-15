package org.example.interfaces.impl;

import org.example.Course;
import org.example.CourseRecommender;
import org.example.services.impl.CourseRecommenderImpl1;
import org.example.services.impl.CourseRecommenderImpl3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class CourseRecommenderTests {

    private CourseRecommender recommender1;
    private CourseRecommender recommender3;

    @BeforeEach
    public void init(){
        recommender1 = new CourseRecommenderImpl1();
        recommender3 = new CourseRecommenderImpl3();
    }

    @Test
    public void CourseRecommenderImpl1_RecommendedCourses_ReturnsListOfCourses(){


        Course c1 = new Course(1, "ADSA", "ADSA Course", 4);
        Course c2 = new Course(2, "Scalable", "Scalable Course", 5);
        List<Course> expectedCourses = List.of(c1, c2);

        List<Course> recommendedCourses = recommender1.recommendedCourses();

        assertNotNull(recommendedCourses);
        assertEquals(expectedCourses.size(), recommendedCourses.size());
        for(int i = 0; i < recommendedCourses.size(); i++){
            assertEquals(expectedCourses.get(i), recommendedCourses.get(i));
        }
    }


    @Test
    public void CourseRecommenderImpl3_RecommendedCourses_ReturnsListOfCourses(){


        Course c1 = new Course(1, "PE", "PE Course", 6);
        Course c2 = new Course(2, "ART", "ART Course", 4);
        List<Course> expectedCourses = List.of(c1, c2);

        List<Course> recommendedCourses = recommender3.recommendedCourses();

        assertNotNull(recommendedCourses);
        assertEquals(expectedCourses.size(), recommendedCourses.size());
        for(int i = 0; i < recommendedCourses.size(); i++){
            assertEquals(expectedCourses.get(i), recommendedCourses.get(i));
        }
    }
}
