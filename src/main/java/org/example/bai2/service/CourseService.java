package org.example.bai2.service;

import org.example.bai2.model.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    private static final Logger logger =
            LoggerFactory.getLogger(CourseService.class);

    private final List<Course> courses = new ArrayList<>();

    private Long currentId = 1L;

    public List<Course> getAllCourses() {
        return courses;
    }

    public Course getCourseById(Long id) {

        Course course = courses.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (course == null) {
            logger.warn("Course not found with id: {}", id);
        }

        return course;
    }

    public Course createCourse(Course course) {

        course.setId(currentId++);

        courses.add(course);

        logger.info("Created course successfully: {}", course.getCourseName());

        return course;
    }

    public Course updateCourse(Long id, Course newCourse) {

        Course oldCourse = getCourseById(id);

        if (oldCourse == null) {
            logger.warn("Cannot update. Course not found with id: {}", id);
            return null;
        }

        oldCourse.setCourseName(newCourse.getCourseName());
        oldCourse.setInstructor(newCourse.getInstructor());
        oldCourse.setDurationHours(newCourse.getDurationHours());
        oldCourse.setFee(newCourse.getFee());

        logger.info("Updated course successfully with id: {}", id);

        return oldCourse;
    }

    public boolean deleteCourse(Long id) {

        Course course = getCourseById(id);

        if (course == null) {
            logger.warn("Cannot delete. Course not found with id: {}", id);
            return false;
        }

        courses.remove(course);

        logger.info("Deleted course with id: {}", id);

        return true;
    }
}
