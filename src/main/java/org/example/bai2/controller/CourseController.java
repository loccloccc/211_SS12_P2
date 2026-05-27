package org.example.bai2.controller;



import org.example.bai2.model.Course;
import org.example.bai2.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private static final Logger logger =
            LoggerFactory.getLogger(CourseController.class);

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {

        logger.info("GET request: /api/courses");

        try {
            List<Course> courses = courseService.getAllCourses();

            return ResponseEntity.ok(courses);

        } catch (RuntimeException e) {

            logger.error("Error while getting all courses", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server error");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        logger.info("GET request: /api/courses/{}", id);

        try {

            Course course = courseService.getCourseById(id);

            if (course == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Course not found");
            }

            return ResponseEntity.ok(course);

        } catch (RuntimeException e) {

            logger.error("Error while getting course by id", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server error");
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Course course) {

        logger.info("POST request: /api/courses");

        try {

            Course created = courseService.createCourse(course);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(created);

        } catch (RuntimeException e) {

            logger.error("Error while creating course", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server error");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody Course course) {

        logger.info("PUT request: /api/courses/{}", id);

        try {

            Course updated = courseService.updateCourse(id, course);

            if (updated == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Course not found");
            }

            return ResponseEntity.ok(updated);

        } catch (RuntimeException e) {

            logger.error("Error while updating course", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server error");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        logger.info("DELETE request: /api/courses/{}", id);

        try {

            boolean deleted = courseService.deleteCourse(id);

            if (!deleted) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Course not found");
            }

            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {

            logger.error("Error while deleting course", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server error");
        }
    }
}
