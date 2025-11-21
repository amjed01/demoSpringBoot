package com.example.demo.dto;

import com.example.demo.model.Student;
import com.example.demo.model.University;

import java.util.List;
import java.util.ArrayList;

public class StudentWithCoursesDTO {
    private Integer id;
    private String name;
    private String address;
    private University university;
    private List<String> enrolledCourseNames;
    private List<Integer> enrolledCourseIds;

    // Constructors
    public StudentWithCoursesDTO() {
        this.enrolledCourseNames = new ArrayList<>();
        this.enrolledCourseIds = new ArrayList<>();
    }

    public StudentWithCoursesDTO(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.address = student.getAddress();
        this.university = student.getUniversity();
        this.enrolledCourseIds = new ArrayList<>(student.getEnrolledCourseIds());
        this.enrolledCourseNames = new ArrayList<>();
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public University getUniversity() { return university; }
    public void setUniversity(University university) { this.university = university; }

    public List<String> getEnrolledCourseNames() { return enrolledCourseNames; }
    public void setEnrolledCourseNames(List<String> enrolledCourseNames) { 
        this.enrolledCourseNames = enrolledCourseNames; 
    }

    public List<Integer> getEnrolledCourseIds() { return enrolledCourseIds; }
    public void setEnrolledCourseIds(List<Integer> enrolledCourseIds) { 
        this.enrolledCourseIds = enrolledCourseIds; 
    }

    public void addCourseName(String courseName) {
        this.enrolledCourseNames.add(courseName);
    }

    @Override
    public String toString() {
        return "StudentWithCoursesDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", university=" + (university != null ? university.getName() : "null") +
                ", enrolledCourseNames=" + enrolledCourseNames +
                ", enrolledCourseIds=" + enrolledCourseIds +
                '}';
    }
}