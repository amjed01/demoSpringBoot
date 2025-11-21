package com.example.demo.controller;

import com.example.demo.model.University;
import com.example.demo.dto.UniversityDTO;
import com.example.demo.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/university")

public class UniversityController {

    @Autowired
    private UniversityRepository universityRepository;

    @GetMapping("/getAll")
    public ResponseEntity<List<UniversityDTO>> getAllUniversities() {
        try {
            System.out.println("Fetching all universities...");
            List<University> universities = universityRepository.findAll();
            System.out.println("Found " + universities.size() + " universities");

            // Convert to DTO to avoid circular references
            List<UniversityDTO> universityDTOs = universities.stream()
                    .map(univ -> new UniversityDTO(univ.getId(), univ.getName()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(universityDTOs);
        } catch (Exception e) {
            System.out.println("Error fetching universities: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUniversity(@RequestBody University university) {
        try {
            System.out.println("Adding university: " + university.getName());

            // Check if university with same name already exists
            University existingUniv = universityRepository.findByName(university.getName());
            if (existingUniv != null) {
                return ResponseEntity.badRequest().body("University with this name already exists");
            }

            University savedUniversity = universityRepository.save(university);
            System.out.println("University saved with ID: " + savedUniversity.getId());

            return ResponseEntity.ok("New university added successfully with ID: " + savedUniversity.getId());
        } catch (Exception e) {
            System.out.println("Error adding university: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error adding university: " + e.getMessage());
        }
    }

    // Test endpoint to add sample data
    @PostMapping("/add-test")
    public ResponseEntity<String> addTestUniversities() {
        try {
            University univ1 = new University("Harvard University");
            University univ2 = new University("Stanford University");
            University univ3 = new University("MIT");

            universityRepository.save(univ1);
            universityRepository.save(univ2);
            universityRepository.save(univ3);

            return ResponseEntity.ok("Added 3 test universities");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding test universities: " + e.getMessage());
        }
    }
}