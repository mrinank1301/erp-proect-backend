package com.erp_project.project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erp_project.project.dto.CarDTO;
import com.erp_project.project.entity.Car;
import com.erp_project.project.repository.CarRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarService {
    
    private final CarRepository carRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public List<CarDTO> getAllCars() {
        return carRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<CarDTO> getCarsByStatus(String status) {
        return carRepository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public CarDTO getCarById(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
        return convertToDTO(car);
    }
    
    @Transactional
    public CarDTO createCar(CarDTO carDTO) {
        Car car = convertToEntity(carDTO);
        car.setStatus("available");
        Car savedCar = carRepository.save(car);
        return convertToDTO(savedCar);
    }
    
    @Transactional
    public CarDTO updateCar(Long id, CarDTO carDTO) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
        
        car.setName(carDTO.getName());
        car.setBrand(carDTO.getBrand());
        car.setModel(carDTO.getModel());
        car.setYear(carDTO.getYear());
        car.setDescription(carDTO.getDescription());
        car.setPrice(carDTO.getPrice());
        car.setImageUrl(carDTO.getImageUrl());
        car.setColor(carDTO.getColor());
        car.setFuelType(carDTO.getFuelType());
        car.setTransmission(carDTO.getTransmission());
        car.setMileage(carDTO.getMileage());
        car.setEngineCapacity(carDTO.getEngineCapacity());
        car.setSeats(carDTO.getSeats());
        car.setStatus(carDTO.getStatus());
        
        if (carDTO.getAdditionalImages() != null) {
            try {
                car.setAdditionalImages(objectMapper.writeValueAsString(carDTO.getAdditionalImages()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error processing additional images", e);
            }
        }
        
        if (carDTO.getFeatures() != null) {
            try {
                car.setFeatures(objectMapper.writeValueAsString(carDTO.getFeatures()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error processing features", e);
            }
        }
        
        Car updatedCar = carRepository.save(car);
        return convertToDTO(updatedCar);
    }
    
    @Transactional
    public void deleteCar(Long id) {
        if (!carRepository.existsById(id)) {
            throw new RuntimeException("Car not found with id: " + id);
        }
        carRepository.deleteById(id);
    }
    
    private CarDTO convertToDTO(Car car) {
        CarDTO dto = new CarDTO();
        dto.setId(car.getId());
        dto.setName(car.getName());
        dto.setBrand(car.getBrand());
        dto.setModel(car.getModel());
        dto.setYear(car.getYear());
        dto.setDescription(car.getDescription());
        dto.setPrice(car.getPrice());
        dto.setImageUrl(car.getImageUrl());
        dto.setColor(car.getColor());
        dto.setFuelType(car.getFuelType());
        dto.setTransmission(car.getTransmission());
        dto.setMileage(car.getMileage());
        dto.setEngineCapacity(car.getEngineCapacity());
        dto.setSeats(car.getSeats());
        dto.setStatus(car.getStatus());
        
        if (car.getAdditionalImages() != null) {
            try {
                List<String> images = objectMapper.readValue(
                    car.getAdditionalImages(), 
                    objectMapper.getTypeFactory().constructCollectionType(List.class, String.class)
                );
                dto.setAdditionalImages(images);
            } catch (JsonProcessingException e) {
                dto.setAdditionalImages(List.of());
            }
        }
        
        if (car.getFeatures() != null) {
            try {
                List<String> features = objectMapper.readValue(
                    car.getFeatures(), 
                    objectMapper.getTypeFactory().constructCollectionType(List.class, String.class)
                );
                dto.setFeatures(features);
            } catch (JsonProcessingException e) {
                dto.setFeatures(List.of());
            }
        }
        
        return dto;
    }
    
    private Car convertToEntity(CarDTO dto) {
        Car car = new Car();
        car.setName(dto.getName());
        car.setBrand(dto.getBrand());
        car.setModel(dto.getModel());
        car.setYear(dto.getYear());
        car.setDescription(dto.getDescription());
        car.setPrice(dto.getPrice());
        car.setImageUrl(dto.getImageUrl());
        car.setColor(dto.getColor());
        car.setFuelType(dto.getFuelType());
        car.setTransmission(dto.getTransmission());
        car.setMileage(dto.getMileage());
        car.setEngineCapacity(dto.getEngineCapacity());
        car.setSeats(dto.getSeats());
        car.setStatus(dto.getStatus());
        
        if (dto.getAdditionalImages() != null) {
            try {
                car.setAdditionalImages(objectMapper.writeValueAsString(dto.getAdditionalImages()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error processing additional images", e);
            }
        }
        
        if (dto.getFeatures() != null) {
            try {
                car.setFeatures(objectMapper.writeValueAsString(dto.getFeatures()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error processing features", e);
            }
        }
        
        return car;
    }
}

