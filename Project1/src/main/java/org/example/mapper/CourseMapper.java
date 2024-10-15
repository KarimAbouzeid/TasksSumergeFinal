package org.example.mapper;

import org.example.dtos.CourseDto;
import org.example.entities.Course;
import org.mapstruct.Mapper;

@Mapper
public interface CourseMapper {


    CourseDto courseToCourseDto(Course course);

    Course courseDtoToCourse(CourseDto courseDto);

}
