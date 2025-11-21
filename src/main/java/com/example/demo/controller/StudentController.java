package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Map;
@RestController
@RequestMapping("/student")

public class StudentController {

    @Autowired
    private StudentService studentService;

    // Create - Add new student
    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody Student student) {
        try {
            Student savedStudent = studentService.saveStudent(student);
            return ResponseEntity.ok("New student is added with ID: " + savedStudent.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding student: " + e.getMessage());
        }
    }




    // Update - Update student
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable Integer id, @RequestBody Student student) {
        try {
            Student updatedStudent = studentService.updateStudent(id, student);
            if (updatedStudent != null) {
                return ResponseEntity.ok("Student updated successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating student: " + e.getMessage());
        }
    }

    // Delete - Delete student
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            boolean isDeleted = studentService.deleteStudent(id);
            if (isDeleted) {
                return ResponseEntity.ok("Student deleted successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting student: " + e.getMessage());
        }
    }

    // Search - Search students by various criteria
    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchStudents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer universityId,
            @RequestParam(required = false) String universityName) {
        
        try {
            List<Student> students = studentService.searchStudents(name, universityId, universityName);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Additional specific search endpoints
    @GetMapping("/search/name/{name}")
    public ResponseEntity<List<Student>> searchStudentsByName(@PathVariable String name) {
        try {
            List<Student> students = studentService.searchStudentsByName(name);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/search/university/id/{universityId}")
    public ResponseEntity<List<Student>> searchStudentsByUniversityId(@PathVariable Integer universityId) {
        try {
            List<Student> students = studentService.searchStudentsByUniversityId(universityId);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/search/university/name/{universityName}")
    public ResponseEntity<List<Student>> searchStudentsByUniversityName(@PathVariable String universityName) {
        try {
            List<Student> students = studentService.searchStudentsByUniversityName(universityName);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    // Add to StudentController.java

    // Filter students by course - IMPROVED VERSION
    @GetMapping("/filter/by-course")
    public ResponseEntity<List<Student>> filterStudentsByCourse(
            @RequestParam(required = false) Integer courseId,
            @RequestParam(required = false) String courseName) {
        
        try {
            System.out.println("Filtering students by course - courseId: " + courseId + ", courseName: " + courseName);
            
            List<Student> students;
            
            if (courseId != null) {
                students = studentService.getStudentsByCourseId(courseId);
                System.out.println("Found " + students.size() + " students enrolled in course ID: " + courseId);
            } else if (courseName != null && !courseName.trim().isEmpty()) {
                students = studentService.getStudentsByCourseName(courseName);
                System.out.println("Found " + students.size() + " students enrolled in course: " + courseName);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
            
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            System.out.println("Error filtering students by course: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // Enroll student in course
    @PostMapping("/{studentId}/enroll/{courseId}")
    public ResponseEntity<String> enrollStudentInCourse(
            @PathVariable Integer studentId,
            @PathVariable Integer courseId) {
        
        try {
            Student student = studentService.enrollStudentInCourse(studentId, courseId);
            return ResponseEntity.ok("Student " + studentId + " enrolled in course " + courseId + " successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error enrolling student: " + e.getMessage());
        }
    }

    // Unenroll student from course
    @PostMapping("/{studentId}/unenroll/{courseId}")
    public ResponseEntity<String> unenrollStudentFromCourse(
            @PathVariable Integer studentId,
            @PathVariable Integer courseId) {
        
        try {
            Student student = studentService.unenrollStudentFromCourse(studentId, courseId);
            return ResponseEntity.ok("Student " + studentId + " unenrolled from course " + courseId + " successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error unenrolling student: " + e.getMessage());
        }
    }

    // Get student's enrolled courses
    @GetMapping("/{studentId}/courses")
    public ResponseEntity<List<Integer>> getStudentCourses(@PathVariable Integer studentId) {
        try {
            List<Integer> courseIds = studentService.getStudentCourses(studentId);
            return ResponseEntity.ok(courseIds);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @GetMapping("/test")
public ResponseEntity<String> test() {
    return ResponseEntity.ok("Student controller is working!");
}
// Add this method to your existing StudentController
@GetMapping("/getAllWithSimpleCourseNames")
public ResponseEntity<List<Map<String, Object>>> getAllStudentsWithSimpleCourseNames() {
    try {
        List<Student> students = studentService.getAllStudents();
        
        // Simple course name mapping
        Map<Integer, String> courseNames = new HashMap<>();
        courseNames.put(1, "Python Programming");
        courseNames.put(2, "Calculus I");
        courseNames.put(3, "Data Science");
        courseNames.put(4, "Web Development");
        
        // Convert to simple map with course names
        List<Map<String, Object>> result = new ArrayList<>();
        for (Student student : students) {
            Map<String, Object> studentMap = new HashMap<>();
            studentMap.put("id", student.getId());
            studentMap.put("name", student.getName());
            studentMap.put("address", student.getAddress());
            studentMap.put("university", student.getUniversity());
            studentMap.put("enrolledCourseIds", student.getEnrolledCourseIds());
            
            // Add course names
            List<String> courseNameList = new ArrayList<>();
            for (Integer courseId : student.getEnrolledCourseIds()) {
                String courseName = courseNames.get(courseId);
                if (courseName != null) {
                    courseNameList.add(courseName);
                } else {
                    courseNameList.add("Course " + courseId);
                }
            }
            studentMap.put("enrolledCourseNames", courseNameList);
            
            result.add(studentMap);
        }
        
        return ResponseEntity.ok(result);
    } catch (Exception e) {
        return ResponseEntity.internalServerError().build();
    }
}
}