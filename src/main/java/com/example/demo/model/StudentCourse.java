package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "student_course")
public class StudentCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "student_id", nullable = false)
    private Integer studentId;
    
    @Column(name = "course_id", nullable = false)
    private Integer courseId;
    
    @Column(name = "course_name")
    private String courseName;
    
    // Constructors
    public StudentCourse() {}
    
    public StudentCourse(Integer studentId, Integer courseId, String courseName) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.courseName = courseName;
    }
    
    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }
    
    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }
    
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
}