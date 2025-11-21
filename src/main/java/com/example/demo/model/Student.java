package com.example.demo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashSet;
import java.util.Set;
import java.util.Map;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private University university;

    // Course IDs for database relationships
    @ElementCollection
    @CollectionTable(name = "student_courses", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "course_id")
    private Set<Integer> enrolledCourseIds = new HashSet<>();

    // Transient field for course names (not stored in database)
    @Transient
    private Set<String> enrolledCourseNames = new HashSet<>();

    // Constructors
    public Student() {
    }

    public Student(String name, String address, University university) {
        this.name = name;
        this.address = address;
        this.university = university;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public Set<Integer> getEnrolledCourseIds() {
        return enrolledCourseIds;
    }

    public void setEnrolledCourseIds(Set<Integer> enrolledCourseIds) {
        this.enrolledCourseIds = enrolledCourseIds;
    }

    public Set<String> getEnrolledCourseNames() {
        return enrolledCourseNames;
    }

    public void setEnrolledCourseNames(Set<String> enrolledCourseNames) {
        this.enrolledCourseNames = enrolledCourseNames;
    }

    // Helper methods for course enrollment
    public void enrollInCourse(Integer courseId) {
        this.enrolledCourseIds.add(courseId);
    }

    public void enrollInCourseWithName(Integer courseId, String courseName) {
        this.enrolledCourseIds.add(courseId);
        this.enrolledCourseNames.add(courseName);
    }

    public void unenrollFromCourse(Integer courseId) {
        this.enrolledCourseIds.remove(courseId);
    }

    public boolean isEnrolledInCourse(Integer courseId) {
        return this.enrolledCourseIds.contains(courseId);
    }

    // Method to update course names based on a mapping
    public void updateCourseNames(Map<Integer, String> courseIdToNameMap) {
        this.enrolledCourseNames.clear();
        for (Integer courseId : this.enrolledCourseIds) {
            String courseName = courseIdToNameMap.get(courseId);
            if (courseName != null) {
                this.enrolledCourseNames.add(courseName);
            } else {
                this.enrolledCourseNames.add("Course " + courseId);
            }
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", university=" + (university != null ? university.getName() : "null") +
                ", enrolledCourseIds=" + enrolledCourseIds +
                ", enrolledCourseNames=" + enrolledCourseNames +
                '}';
    }
}