package com.example.Studentcourse.service;
import com.example.Studentcourse.dto.CourseDTO;
import com.example.Studentcourse.dto.EnrollmentDTO;
import com.example.Studentcourse.exception.ResourceNotFoundException;
import com.example.Studentcourse.exception.ValidationException;
import com.example.Studentcourse.model.Course;
import com.example.Studentcourse.model.Student;
import com.example.Studentcourse.repository.CourseRepository;
import com.example.Studentcourse.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public Course createCourse(CourseDTO courseDTO) {
        if (courseRepository.existsByName(courseDTO.getName())) {
            throw new ValidationException("Course already exists with name: " + courseDTO.getName());
        }

        Course course = new Course();
        course.setName(courseDTO.getName());
        course.setCreditHours(courseDTO.getCreditHours());
        course.setDescription(courseDTO.getDescription());
        
        return courseRepository.save(course);
    }

    public void enrollStudentToCourse(EnrollmentDTO enrollmentDTO) {
        Student student = studentRepository.findById(enrollmentDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + enrollmentDTO.getStudentId()));
        
        Course course = courseRepository.findById(enrollmentDTO.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + enrollmentDTO.getCourseId()));
        
        // Add course to student's courses
        student.getCourses().add(course);
        studentRepository.save(student);
    }

    public Set<Student> getStudentsByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));
        
        return course.getStudents();
    }
}
