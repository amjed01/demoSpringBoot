package com.example.demo.repository;

import com.example.demo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
  @Query("SELECT DISTINCT s FROM Student s LEFT JOIN FETCH s.university LEFT JOIN FETCH s.enrolledCourseIds WHERE s.id IN (SELECT sc.studentId FROM StudentCourse sc)")
  List<Student> findAllWithUniversityAndCourses();
  
    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.university")
    List<Student> findAllWithUniversity();

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.university WHERE s.id = :id")
    Optional<Student> findByIdWithUniversity(@Param("id") Integer id);

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.university WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Student> findByNameContainingIgnoreCase(@Param("name") String name);

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.university u WHERE u.id = :universityId")
    List<Student> findByUniversityId(@Param("universityId") Integer universityId);

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.university u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :universityName, '%'))")
    List<Student> findByUniversityNameContainingIgnoreCase(@Param("universityName") String universityName);

    @Query("SELECT s.id as studentId, s.name as studentName, s.address as address, " +
           "u.name as universityName FROM Student s LEFT JOIN s.university u")
    List<Object> findAllStudentsWithUniversity();

    @Query("SELECT s.id as studentId, s.name as studentName, s.address as address, " +
           "u.name as universityName FROM Student s JOIN s.university u WHERE u.name = :univName")
    List<Object> findStudentsByUniversityName(@Param("univName") String univName);
    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.university WHERE s.id IN " +
           "(SELECT sc.studentId FROM StudentCourse sc WHERE sc.courseId = :courseId)")
    List<Student> findByEnrolledCourseId(@Param("courseId") Integer courseId);

    // Find students enrolled in courses with specific names
    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.university WHERE s.id IN " +
           "(SELECT sc.studentId FROM StudentCourse sc WHERE sc.courseName LIKE %:courseName%)")
    List<Student> findByEnrolledCourseName(@Param("courseName") String courseName);

    // Find students enrolled in multiple courses (course IDs)
    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.university WHERE s.id IN " +
           "(SELECT sc.studentId FROM StudentCourse sc WHERE sc.courseId IN :courseIds)")
    List<Student> findByEnrolledCourseIds(@Param("courseIds") List<Integer> courseIds);

}