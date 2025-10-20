package com.erp_project.project.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String brand;
    
    @Column(nullable = false)
    private String model;
    
    @Column(nullable = false)
    private Integer year;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false)
    private Double price;
    
    @Column(name = "image_url")
    private String imageUrl;
    
    @Column(name = "additional_images", columnDefinition = "TEXT")
    private String additionalImages; // JSON array of image URLs
    
    private String color;
    
    @Column(name = "fuel_type")
    private String fuelType;
    
    private String transmission;
    
    private Integer mileage;
    
    @Column(name = "engine_capacity")
    private String engineCapacity;
    
    private Integer seats;
    
    @Column(columnDefinition = "TEXT")
    private String features; // JSON array of features
    
    private String status; // available, sold, reserved
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

