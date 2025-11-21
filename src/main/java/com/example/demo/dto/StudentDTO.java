package com.example.demo.dto;

public class StudentDTO {
    private int id;
    private String name;
    private String address;
    private String universityName;
    private Integer universityId;

    public StudentDTO() {}

    public StudentDTO(int id, String name, String address, String universityName, Integer universityId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.universityName = universityName;
        this.universityId = universityId;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getUniversityName() { return universityName; }
    public void setUniversityName(String universityName) { this.universityName = universityName; }

    public Integer getUniversityId() { return universityId; }
    public void setUniversityId(Integer universityId) { this.universityId = universityId; }
}