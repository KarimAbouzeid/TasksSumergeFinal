package org.example.services;

import org.example.Course;
import org.example.dtos.CourseDto;
import org.example.repository.CourseRepositoryJDBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@Service
@ComponentScan(basePackages = {"org.example.repository"})
public class CourseServiceJDBC {

    private final CourseRepositoryJDBC courseRepositoryJDBC;

    @Autowired
    public CourseServiceJDBC(CourseRepositoryJDBC courseRepositoryJDBC) {
        this.courseRepositoryJDBC = courseRepositoryJDBC;
    }


    public void addCourse(CourseDto courseDto){
        courseRepositoryJDBC.addCourse(courseDto.getId(), courseDto.getName(), courseDto.getDescription(), courseDto.getCredits());
    }

    public void deleteCourse(CourseDto courseDto){
        courseRepositoryJDBC.deleteCourse(courseDto.getId());
    }

    public void updateCourse(int id, CourseDto courseDto){
        courseRepositoryJDBC.updateCourse(courseDto.getId(), courseDto);
    }

    public CourseDto viewCourse(int id){
        Course course = courseRepositoryJDBC.viewCourse(id);
        CourseDto courseDto = new CourseDto();
        courseDto.setId(course.getId());
        courseDto.setName(course.getName());
        courseDto.setDescription(course.getDescription());
        courseDto.setCredits(course.getCredits());
        return courseDto;
    }
};


