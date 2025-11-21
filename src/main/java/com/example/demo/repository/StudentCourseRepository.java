package com.example.demo.repository;

import com.example.demo.model.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Integer> {
    
    List<StudentCourse> findByStudentId(Integer studentId);
    
    @Query("SELECT sc.courseId FROM StudentCourse sc WHERE sc.studentId = :studentId")
    List<Integer> findCourseIdsByStudentId(@Param("studentId") Integer studentId);
    
    void deleteByStudentIdAndCourseId(Integer studentId, Integer courseId);
    
    boolean existsByStudentIdAndCourseId(Integer studentId, Integer courseId);
}