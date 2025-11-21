package com.example.demo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Table(name = "university")
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore  // ⭐⭐⭐ THIS IS THE CRITICAL FIX ⭐⭐⭐
    private List<Student> students;

    // Constructors
    public University() {}

    public University(String name) {
        this.name = name;
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

    public List<Student> getStudents() { 
        return students; 
    }
    
    public void setStudents(List<Student> students) { 
        this.students = students; 
    }

    @Override
    public String toString() {
        return "University{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}