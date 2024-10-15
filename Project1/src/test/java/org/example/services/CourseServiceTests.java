package org.example.services;

import org.example.exception.CourseNotFoundException;
import org.example.dtos.CourseDto;
import org.example.entities.Course;
import org.example.filter.mapper.CourseMapper;
import org.example.repository.CourseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class CourseServiceTests {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseServiceJDBC courseServiceJDBC;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseService courseService;

    private int courseId;
    private Course course;
    private CourseDto courseDto;

    @BeforeEach
    public void init(){
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
    public void CourseService_AddCourse_ReturnsCourseDto() {

        when(courseMapper.courseDtoToCourse(any(CourseDto.class))).thenReturn(course);
        when(courseRepository.save(course)).thenReturn(course);
        when(courseMapper.courseToCourseDto(course)).thenReturn(courseDto);

        CourseDto resultDto = courseService.addCourse(courseDto);


        Assertions.assertEquals(courseDto, resultDto);

    }

    @Test
    public void CourseService_ViewCourse_ReturnsCourseDto() {

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(courseMapper.courseToCourseDto(course)).thenReturn(courseDto);


        CourseDto returnedCourseDto = courseService.viewCourse(courseId);

        Assertions.assertEquals(courseDto, returnedCourseDto);

    }

    @Test void CourseService_ViewCourse_ReturnsCourseNotFoundException(){


        CourseNotFoundException exception = new CourseNotFoundException("Course Not Found");
        when(courseRepository.findById(any(Integer.class))).thenThrow(exception);


        assertThrows(CourseNotFoundException.class, ()-> courseService.viewCourse(courseId));
    }

    @Test
    public void CourseService_DeleteCourse_ReturnsVoid(){

        when(courseRepository.findById(any(Integer.class))).thenReturn(Optional.of(course));
        courseService.deleteCourse(courseId);

        verify(courseRepository).deleteById(courseId);
    }

    @Test
    public void CourseService_DeleteCourse_ReturnsException(){


        when(courseRepository.findById(any(Integer.class))).thenThrow(new CourseNotFoundException("Course Not Found"));

        assertThrows(CourseNotFoundException.class, ()-> courseService.deleteCourse(courseId));
    }

    @Test
    public void CourseService_ViewAllCourses_ReturnsPageOfCourseDto(){

        Course course1 = new Course();
        course1.setName("Scalable");
        course1.setDescription("Scalable Course");
        course1.setCredits(3);
        Course course2 = new Course();
        course2.setName("ADSA");
        course2.setDescription("ADSA Course");
        course2.setCredits(6);
        Course course3 = new Course();
        course3.setName("Security");
        course3.setDescription("Security Course");
        course3.setCredits(4);

        List<Course> courses = List.of(course1, course2, course3);

        CourseDto courseDto1 = new CourseDto();
        courseDto1.setName("Scalable");
        courseDto1.setDescription("Scalable Course");
        courseDto1.setCredits(3);
        CourseDto courseDto2 = new CourseDto();
        courseDto2.setName("ADSA");
        courseDto2.setDescription("ADSA Course");
        courseDto2.setCredits(6);
        CourseDto courseDto3 = new CourseDto();
        courseDto3.setName("Security");
        courseDto3.setDescription("Security Course");
        courseDto3.setCredits(4);


        Page<Course> pageCourseDtos = new PageImpl<>(courses);

        Pageable pageable = Mockito.mock(Pageable.class);

        when(courseRepository.findAll(any(Pageable.class))).thenReturn(pageCourseDtos);
        when(courseMapper.courseToCourseDto(course1)).thenReturn(courseDto1);
        when(courseMapper.courseToCourseDto(course2)).thenReturn(courseDto2);
        when(courseMapper.courseToCourseDto(course3)).thenReturn(courseDto3);


        Page<CourseDto> returnedPageCourseDtos = courseService.viewAllCourses(pageable);

        Assertions.assertNotNull(returnedPageCourseDtos);
        Assertions.assertEquals(returnedPageCourseDtos.getTotalElements(), 3);
        Assertions.assertEquals("Scalable", returnedPageCourseDtos.getContent().get(0).getName());
        Assertions.assertEquals("ADSA", returnedPageCourseDtos.getContent().get(1).getName());
        Assertions.assertEquals(4, returnedPageCourseDtos.getContent().get(2).getCredits());
        Assertions.assertEquals(courseDto1, returnedPageCourseDtos.getContent().get(0));
    }

    @Test
    public void CourseService_UpdateCourse_ReturnsCourseDto(){
        Course course = new Course();
        course.setName("ADSA");
        course.setDescription("Egypt Course");
        course.setCredits(2);

        CourseDto courseDto = new CourseDto();
        courseDto.setName("Scalable");
        courseDto.setDescription("Scalable Course");
        courseDto.setCredits(5);

        CourseDto courseDtoExpected = new CourseDto();
        courseDtoExpected.setName("Scalable");
        courseDtoExpected.setDescription("Scalable Course");
        courseDtoExpected.setCredits(5);

        when(courseRepository.findById(any(Integer.class))).thenReturn(Optional.of(course));
        when(courseRepository.save(any(Course.class))).thenReturn(course);
        when(courseMapper.courseToCourseDto(course)).thenReturn(courseDtoExpected);

        CourseDto updatedDto = courseService.updateCourse(1, courseDto);

        Assertions.assertNotNull(updatedDto);
        Assertions.assertEquals(courseDtoExpected, updatedDto);

    }

    @Test
    public void CourseService_UpdateCourseJDBC_ReturnsVoid(){

        courseServiceJDBC.updateCourse(courseId, courseDto);
        verify(courseServiceJDBC).updateCourse(any(Integer.class), any(CourseDto.class));
    }


}
