package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.model.StudentCourse;
import com.example.demo.model.University;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.StudentCourseRepository;
import com.example.demo.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;
    @Autowired
    private UniversityRepository universityRepository;

    public Student saveStudent(Student student) {
        // Handle university reference properly
        if (student.getUniversity() != null && student.getUniversity().getId() > 0) {
            // University ID is provided, fetch the entity
            University university = universityRepository.findById(student.getUniversity().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "University not found with id: " + student.getUniversity().getId()));
            student.setUniversity(university);
        } else if (student.getUniversity() != null && student.getUniversity().getName() != null) {
            // University name is provided, find by name
            University university = universityRepository.findByName(student.getUniversity().getName());
            if (university == null) {
                throw new RuntimeException("University not found with name: " + student.getUniversity().getName());
            }
            student.setUniversity(university);
        }
        return studentRepository.save(student);
    }
 
    public List<Student> getAllStudents() {
        return studentRepository.findAllWithUniversity();
    }

    public List<Object> getAllStudentsUniversity() {
        return studentRepository.findAllStudentsWithUniversity();
    }

    public List<Object> findStudentsByUniversity(String univName) {
        return studentRepository.findStudentsByUniversityName(univName);
    }

    public boolean deleteStudent(Integer id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Student updateStudent(Integer id, Student studentDetails) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            student.setName(studentDetails.getName());
            student.setAddress(studentDetails.getAddress());

            // Update university if provided
            if (studentDetails.getUniversity() != null) {
                if (studentDetails.getUniversity().getId() > 0) {
                    University university = universityRepository.findById(studentDetails.getUniversity().getId())
                            .orElseThrow(() -> new RuntimeException(
                                    "University not found with id: " + studentDetails.getUniversity().getId()));
                    student.setUniversity(university);
                } else if (studentDetails.getUniversity().getName() != null) {
                    University university = universityRepository.findByName(studentDetails.getUniversity().getName());
                    if (university == null) {
                        throw new RuntimeException(
                                "University not found with name: " + studentDetails.getUniversity().getName());
                    }
                    student.setUniversity(university);
                }
            }

            return studentRepository.save(student);
        }
        return null;
    }

    public Optional<Student> getStudentById(Integer id) {
        return studentRepository.findByIdWithUniversity(id);
    }

    // Search methods
    public List<Student> searchStudentsByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Student> searchStudentsByUniversityId(Integer universityId) {
        return studentRepository.findByUniversityId(universityId);
    }

    public List<Student> searchStudentsByUniversityName(String universityName) {
        return studentRepository.findByUniversityNameContainingIgnoreCase(universityName);
    }

    // Combined search method
    public List<Student> searchStudents(String name, Integer universityId, String universityName) {
        if (name != null && !name.trim().isEmpty()) {
            return searchStudentsByName(name);
        } else if (universityId != null) {
            return searchStudentsByUniversityId(universityId);
        } else if (universityName != null && !universityName.trim().isEmpty()) {
            return searchStudentsByUniversityName(universityName);
        } else {
            return getAllStudents();
        }
    }

     // Course filtering methods
    public List<Student> getStudentsByCourseId(Integer courseId) {
        // Get all students and filter by course enrollment
        List<Student> allStudents = getAllStudents();
        return allStudents.stream()
                .filter(student -> student.isEnrolledInCourse(courseId))
                .collect(Collectors.toList());
    }

    public List<Student> getStudentsByCourseName(String courseName) {
        // For course name filtering, we'll need to call the Course service
        // For now, return empty list as this requires inter-service communication
        System.out.println("Course name filtering requires Course service integration");
        return List.of();
    }

    // Enrollment methods
    public Student enrollStudentInCourse(Integer studentId, Integer courseId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.enrollInCourse(courseId);
            return studentRepository.save(student);
        }
        throw new RuntimeException("Student not found with id: " + studentId);
    }

    public Student unenrollStudentFromCourse(Integer studentId, Integer courseId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.unenrollFromCourse(courseId);
            return studentRepository.save(student);
        }
        throw new RuntimeException("Student not found with id: " + studentId);
    }

    public List<Integer> getStudentCourses(Integer studentId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isPresent()) {
            return List.copyOf(studentOpt.get().getEnrolledCourseIds());
        }
        throw new RuntimeException("Student not found with id: " + studentId);
    }

    // Simple method to simulate course enrollments (for testing)
    public List<Student> getStudentsByCourseIdSimulated(Integer courseId) {
        List<Student> allStudents = getAllStudents();
        List<Student> filteredStudents = new ArrayList<>();

        // Simple simulation: include students based on their ID and course ID
        for (Student student : allStudents) {
            // Simple logic: if student ID is even and course ID is even, or both odd
            boolean shouldInclude = (student.getId() % 2 == 0 && courseId % 2 == 0) ||
                    (student.getId() % 2 == 1 && courseId % 2 == 1);

            if (shouldInclude) {
                filteredStudents.add(student);
            }
        }

        System.out.println("Simulated filtering: " + filteredStudents.size() + " students for course " + courseId);
        return filteredStudents;
    }
    
}