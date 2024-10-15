package org.example.controller;


import jakarta.validation.Valid;
import org.example.dtos.CourseDto;
import org.example.services.CourseService;
import org.example.services.CourseServiceJDBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;
    private final CourseServiceJDBC courseServiceJDBC;

    @Autowired
    public CourseController(CourseService courseService, CourseServiceJDBC courseServiceJDBC) {
        this.courseService = courseService;
        this.courseServiceJDBC = courseServiceJDBC;
    }


    @PostMapping(value = "/addCourse/")
    public ResponseEntity<CourseDto> addCourse( @Valid @RequestBody CourseDto courseDto) {
        CourseDto returnedCourse = courseService.addCourse(courseDto);
        return ResponseEntity.ok(returnedCourse);
    }

    @PutMapping(value = "/updateCourse/{id}")
    public ResponseEntity<CourseDto> updateCourse( @Valid @RequestBody CourseDto courseDto, @PathVariable int id) {
        CourseDto returnedCourseDto = courseService.updateCourse(id, courseDto);
        return ResponseEntity.ok(returnedCourseDto);

    }
    @PutMapping(value = "/updateCourseJDBC/{id}/")
    public ResponseEntity<String> updateCourse(@PathVariable("id") int id, @RequestBody CourseDto courseDto) {

        courseServiceJDBC.updateCourse(id, courseDto);

        return ResponseEntity.ok("Course updated");
    }

    @GetMapping(value = "/viewCourse/{id}/")
    public ResponseEntity<CourseDto> viewCourse(@PathVariable int id) {
        CourseDto courseDto = courseService.viewCourse(id);
        return ResponseEntity.ok(courseDto);
    }

    @DeleteMapping(value = "/deleteCourse/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable int id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Course deleted");
    }
    @GetMapping("/getRecommendedCourses/")
    public ResponseEntity<Page<CourseDto>> getRecommendedCourses(@RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam String[] sort) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sort));
        Page<CourseDto> coursesDto = courseService.viewAllCourses(pageable);
        return ResponseEntity.ok(coursesDto);
    }

}
