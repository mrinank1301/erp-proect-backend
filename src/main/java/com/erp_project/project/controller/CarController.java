package com.erp_project.project.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erp_project.project.dto.CarDTO;
import com.erp_project.project.service.CarService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {
    
    private final CarService carService;
    
    @GetMapping
    public ResponseEntity<List<CarDTO>> getAllCars(@RequestParam(required = false) String status) {
        List<CarDTO> cars;
        if (status != null && !status.isEmpty()) {
            cars = carService.getCarsByStatus(status);
        } else {
            cars = carService.getAllCars();
        }
        return ResponseEntity.ok(cars);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable Long id) {
        try {
            CarDTO car = carService.getCarById(id);
            return ResponseEntity.ok(car);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<?> createCar(@Valid @RequestBody CarDTO carDTO, HttpServletRequest request) {
        String role = (String) request.getAttribute("userRole");
        
        // Debug logging
        System.out.println("Create Car - Received role: " + role);
        System.out.println("Create Car - User ID: " + request.getAttribute("userId"));
        
        if (role == null || !role.equals("admin")) {
            System.err.println("Access denied - Role: " + role);
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Only admins can create cars. Your role: " + (role != null ? role : "null")));
        }
        
        try {
            CarDTO createdCar = carService.createCar(carDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCar);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCar(@PathVariable Long id, 
                                       @Valid @RequestBody CarDTO carDTO, 
                                       HttpServletRequest request) {
        String role = (String) request.getAttribute("userRole");
        
        if (role == null || !role.equals("admin")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Only admins can update cars"));
        }
        
        try {
            CarDTO updatedCar = carService.updateCar(id, carDTO);
            return ResponseEntity.ok(updatedCar);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable Long id, HttpServletRequest request) {
        String role = (String) request.getAttribute("userRole");
        
        if (role == null || !role.equals("admin")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Only admins can delete cars"));
        }
        
        try {
            carService.deleteCar(id);
            return ResponseEntity.ok(Map.of("message", "Car deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}

