package org.example.mapper;

import javax.annotation.processing.Generated;
import org.example.dtos.CourseDto;
import org.example.entities.Course;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-16T01:34:48+0300",
    comments = "version: 1.6.0, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
public class CourseMapperImpl implements CourseMapper {

    @Override
    public CourseDto courseToCourseDto(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseDto.CourseDtoBuilder courseDto = CourseDto.builder();

        courseDto.id( course.getId() );
        courseDto.name( course.getName() );
        courseDto.description( course.getDescription() );
        courseDto.credits( course.getCredits() );

        return courseDto.build();
    }

    @Override
    public Course courseDtoToCourse(CourseDto courseDto) {
        if ( courseDto == null ) {
            return null;
        }

        Course.CourseBuilder course = Course.builder();

        course.id( courseDto.getId() );
        course.name( courseDto.getName() );
        course.description( courseDto.getDescription() );
        if ( courseDto.getCredits() != null ) {
            course.credits( courseDto.getCredits() );
        }

        return course.build();
    }
}
