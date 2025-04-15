package com.example.Studentcourse.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Student extends Auditable {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @Column(nullable = false)
 private String name;
 
 @Column(nullable = false, unique = true)
 private String email;

 @ManyToMany(fetch = FetchType.LAZY)
 @JoinTable(
     name = "student_courses",
     joinColumns = @JoinColumn(name = "student_id"),
     inverseJoinColumns = @JoinColumn(name = "course_id")
 )
 private Set<Course> courses = new HashSet<>();
 
 // Explicit getters and setters
 public Long getId() {
     return id;
 }

 public void setId(Long id) {
     this.id = id;
 }

 public String getName() {
     return name;
 }

 public void setName(String name) {
     this.name = name;
 }

 public String getEmail() {
     return email;
 }

 public void setEmail(String email) {
     this.email = email;
 }

 public Set<Course> getCourses() {
     return courses;
 }

 public void setCourses(Set<Course> courses) {
     this.courses = courses;
 }
}