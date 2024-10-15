package org.example.mapstruct;


import org.example.dtos.CourseDto;
import org.example.entities.Course;
import org.example.mapper.CourseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CourseMapperTests {


    private CourseMapper courseMapper;

    private int courseId;
    private Course course;
    private CourseDto courseDto;


    @BeforeEach
    public void init(){

        courseMapper = Mappers.getMapper(CourseMapper.class);

        courseId = 1;

        course = new Course();
        course.setName("Scalable");
        course.setDescription("Scalable Course");
        course.setCredits(5);

        courseDto = new CourseDto();
        courseDto.setName("Scalable");
        courseDto.setDescription("Scalable Course");
        courseDto.setCredits(5);
    }

    @Test
    public void CourseMapper_CourseToCourseDto_ReturnsCourseDto(){


        CourseDto resultCourseDto = courseMapper.courseToCourseDto(course);

        assertNotNull(resultCourseDto);
        assertEquals(courseDto, resultCourseDto);
    }

    @Test
    public void CourseMapper_CourseDtoToCourse_ReturnsCourse(){

        Course resultCourse = courseMapper.courseDtoToCourse(courseDto);
        assertNotNull(resultCourse);
        assertEquals(course, resultCourse );
    }
}
