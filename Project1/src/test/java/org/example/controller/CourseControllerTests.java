package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.example.dtos.CourseDto;
import org.example.entities.Course;
import org.example.exception.CourseNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CourseController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CourseControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @MockBean
    private CourseServiceJDBC courseServiceJDBC;

    @Autowired
    private ObjectMapper objectMapper;

    private int courseId;
    private Course course;
    private CourseDto courseDto;
    private CourseDto courseDto1;
    private CourseDto courseDto2;
    private CourseDto courseDto3;

    private WireMockServer wiremock;


    @Autowired
    private Environment env;

    @BeforeEach
    public void init(){

        objectMapper = new ObjectMapper();
        courseId = 1;

        course = new Course();
        course.setName("Scalable");
        course.setDescription("Scalable Course");
        course.setCredits(5);

        courseDto = new CourseDto();
        courseDto.setName("Scalable");
        courseDto.setDescription("Scalable Course");
        courseDto.setCredits(5);
        courseDto1 = new CourseDto();
        courseDto1.setName("Scalable");
        courseDto1.setDescription("Scalable Course");
        courseDto1.setCredits(3);
        courseDto2 = new CourseDto();
        courseDto2.setName("ADSA");
        courseDto2.setDescription("ADSA Course");
        courseDto2.setCredits(6);
        courseDto3 = new CourseDto();
        courseDto3.setName("Security");
        courseDto3.setDescription("Security Course");
        courseDto3.setCredits(4);

    }

    @Test
    public void CourseController_AddCourse_ReturnsCourseDto() throws Exception{

        when(courseService.addCourse(any(CourseDto.class))).thenReturn(courseDto);

        ResultActions response = mockMvc.perform(post("/course/addCourse/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseDto)));

        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.name").value(course.getName()));
        response.andExpect(jsonPath("$.description").value(course.getDescription()));
        response.andExpect(jsonPath("$.credits").value(course.getCredits()));
    }

    @Test
    public void CourseController_UpdateCourse_ReturnsCourseDto() throws Exception{

        when(courseService.updateCourse(any(Integer.class), any(CourseDto.class))).thenReturn(courseDto);

        ResultActions response = mockMvc.perform(put("/course/updateCourse/{courseId}", courseId)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(courseDto))
        );

        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.name").value(course.getName()));
        response.andExpect(jsonPath("$.description").value(course.getDescription()));
        response.andExpect(jsonPath("$.credits").value(course.getCredits()));

    }


    @Test
    public void CourseController_ViewCourse_ReturnsCourseDto() throws Exception{

        when(courseService.viewCourse(any(Integer.class))).thenReturn(courseDto);

        ResultActions response = mockMvc.perform(get("/course/viewCourse/{courseId}/", courseId)
                .contentType(APPLICATION_JSON));

        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.name").value(course.getName()));
        response.andExpect(jsonPath("$.description").value(course.getDescription()));
        response.andExpect(jsonPath("$.credits").value(course.getCredits()));
    }

    @Test
    public void CourseController_DeleteCourse_ReturnsString() throws Exception{

        doNothing().when(courseService).deleteCourse(any(Integer.class));


        ResultActions response = mockMvc.perform(delete("/course/deleteCourse/{courseId}", courseId)
        .contentType(APPLICATION_JSON));


        verify(courseService).deleteCourse(any(Integer.class));

        response.andExpect(status().isOk());
        response.andExpect(content().string("Course deleted"));
    }

    @Test
    public void CourseController_GetRecommendedCourses_ReturnsPageOfCourseDto() throws Exception{

        Page<CourseDto> coursesDtoPage = new PageImpl<>(List.of(courseDto1, courseDto2, courseDto3));
        String[] sort = new String[]{"name"};
        when(courseService.viewAllCourses(any(Pageable.class))).thenReturn(coursesDtoPage);

        ResultActions response = mockMvc.perform(get("/course/getRecommendedCourses/")
                .contentType(APPLICATION_JSON)
                .param("pageNumber", "1")
                .param("pageSize", "2")
                .param("sort", sort));

        response.andExpect(status().isOk());
    }

    @Test
    public void CourseController_UpdateCourseJDBC_ReturnsCourseDto() throws Exception{

        ResultActions response = mockMvc.perform(put("/course/updateCourseJDBC/{id}/", courseId)
        .contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(courseDto)));

        verify(courseServiceJDBC).updateCourse(any(Integer.class), any(CourseDto.class));
        response.andExpect(status().isOk());
        response.andExpect(content().string("Course updated"));


    }

    @Test
    public void CourseController_CourseNotFound_ReturnsString() throws Exception{

        CourseNotFoundException courseNotFoundException = new CourseNotFoundException("Course not found");
        when(courseService.viewCourse(any(Integer.class))).thenThrow(courseNotFoundException);

        mockMvc.perform(get("/course/viewCourse/{id}/", courseId))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Course not found"));

    }



}


