package org.example.repository;

import org.example.Course;
import org.example.CourseRecommender;
import org.example.dtos.CourseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
@ComponentScan(basePackages = {"org.example.interfaces.impl"})
public class CourseRepositoryJDBC {

    CourseRecommender courseRecommender;
    JdbcTemplate jdbcTemplate;


    public CourseRepositoryJDBC(@Qualifier("courseRecommenderImpl3") CourseRecommender courseRecommender, JdbcTemplate jdbcTemplate) {
        this.courseRecommender = courseRecommender;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public void setCourseRecommender(CourseRecommender courseRecommender) {
        this.courseRecommender = courseRecommender;
    }

    public void addCourse (int id, String name, String description, int credits){
        String sql = "INSERT INTO course(id, name, description, credits) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, id, name, description, credits);
    }

    public void updateCourse (int id, CourseDto courseDto){
        String name;
        String description;
        int credits;
        name = courseDto.getName();
        description = courseDto.getDescription();
        credits = courseDto.getCredits();
        String sql = "UPDATE course SET name = ?, description = ?, credits = ? WHERE id = ?";
        jdbcTemplate.update(sql, name, description, credits, id);

    }

    public Course viewCourse (int id){
        String sql = "SELECT * FROM course WHERE id = ?";
        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum)-> new Course(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("credits")
                ), id);
    }

    public void deleteCourse (int id){
        String sql = "DELETE FROM course WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Course> getRecommendedCourses(int num){
        String sql = "SELECT * FROM course ORDER BY id LIMIT ?";
        List <Course> courses = jdbcTemplate.query(sql, new RowMapper<Course>() {
            @Override
            public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setDescription(rs.getString("description"));
                course.setCredits(rs.getInt("credits"));
                return course;
            }
        }, num);
        return courses;
    }
}
