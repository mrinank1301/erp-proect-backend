package com.erp_project.project.dto;

import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
    
    private Long id;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotBlank(message = "Brand is required")
    private String brand;
    
    @NotBlank(message = "Model is required")
    private String model;
    
    @NotNull(message = "Year is required")
    @Min(value = 1900, message = "Year must be after 1900")
    @Max(value = 2100, message = "Year must be before 2100")
    private Integer year;
    
    private String description;
    
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;
    
    private String imageUrl;
    
    private List<String> additionalImages;
    
    private String color;
    
    private String fuelType;
    
    private String transmission;
    
    private Integer mileage;
    
    private String engineCapacity;
    
    private Integer seats;
    
    private List<String> features;
    
    private String status;
}

