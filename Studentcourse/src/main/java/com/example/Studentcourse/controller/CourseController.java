package com.example.Studentcourse.controller;
import com.example.Studentcourse.dto.CourseDTO;
import com.example.Studentcourse.dto.EnrollmentDTO;
import com.example.Studentcourse.model.Course;
import com.example.Studentcourse.model.Student;
import com.example.Studentcourse.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@Valid @RequestBody CourseDTO courseDTO) {
        Course createdCourse = courseService.createCourse(courseDTO);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @PostMapping("/enroll")
    public ResponseEntity<String> enrollStudentToCourse(@Valid @RequestBody EnrollmentDTO enrollmentDTO) {
        courseService.enrollStudentToCourse(enrollmentDTO);
        return new ResponseEntity<>("Student enrolled successfully", HttpStatus.OK);
    }

    @GetMapping("/{courseId}/students")
    public ResponseEntity<Set<Student>> getStudentsByCourseId(@PathVariable Long courseId) {
        Set<Student> students = courseService.getStudentsByCourseId(courseId);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
}