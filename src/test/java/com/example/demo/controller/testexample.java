package com.example.demo.controller;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

import com.example.demo.repository.StudentRepository;
import com.example.demo.model.Student;

// Indicates this is a Spring Boot test that loads the full application context
@SpringBootTest
// Specifies the order in which test methods will be executed
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ControllerTest {
// Injects the StudentRepository bean from the Spring context
@Autowired
private StudentRepository studentRepository;
// First test: verifies that a student can be saved to the repository
@Test
@Order(1) // Ensures this test runs first
void shouldSaveStudent() {
// Create a new student object
Student student = new Student();
student.setName("Charlie");
student.setAddress("Algeria");
// Save the student to the H2 in-memory database
studentRepository.save(student);
// Assert that the repository now contains exactly one record
assertThat(studentRepository.count()).isEqualTo(1);
}
// Second test: verifies that all students can be retrieved correctly
@Test
@Order(2) // Ensures this test runs after shouldSaveStudent()
void shouldFindAllStudents() {
// Fetch all students from the repository
List<Student> students = studentRepository.findAll();
// Assert that there is exactly one student in the list
assertThat(students).hasSize(1);

// Assert that the student's name is "Charlie"
assertThat(students.get(0).getName()).isEqualTo("Charlie");
}
}