package com.erp_project.project.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.erp_project.project.service.SupabaseStorageService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class UploadController {
    
    private final SupabaseStorageService supabaseStorageService;
    
    @PostMapping("/image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file, 
                                        HttpServletRequest request) {
        String role = (String) request.getAttribute("userRole");
        
        // Debug logging
        System.out.println("Upload Image - Received role: " + role);
        System.out.println("Upload Image - User ID: " + request.getAttribute("userId"));
        System.out.println("Upload Image - Email: " + request.getAttribute("userEmail"));
        
        if (role == null || !role.equals("admin")) {
            System.err.println("Access denied - Role: " + role);
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Only admins can upload images. Your role: " + (role != null ? role : "null")));
        }
        
        // Validate file
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Please select a file to upload"));
        }
        
        // Validate file type
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Only image files are allowed"));
        }
        
        try {
            // Upload to Supabase Storage
            String fileUrl = supabaseStorageService.uploadFile(file);
            
            return ResponseEntity.ok(Map.of(
                "url", fileUrl,
                "message", "File uploaded successfully to Supabase Storage"
            ));
        } catch (IOException e) {
            System.err.println("Upload error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to upload file: " + e.getMessage()));
        }
    }
    
    @PostMapping("/images")
    public ResponseEntity<?> uploadMultipleImages(@RequestParam("files") MultipartFile[] files, 
                                                  HttpServletRequest request) {
        String role = (String) request.getAttribute("userRole");
        
        if (role == null || !role.equals("admin")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Only admins can upload images"));
        }
        
        if (files == null || files.length == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Please select at least one file to upload"));
        }
        
        List<Map<String, String>> uploadedFiles = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        
        for (MultipartFile file : files) {
            // Validate file
            if (file.isEmpty()) {
                errors.add("Skipped empty file");
                continue;
            }
            
            // Validate file type
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                errors.add("Skipped non-image file: " + file.getOriginalFilename());
                continue;
            }
            
            try {
                // Upload to Supabase Storage
                String fileUrl = supabaseStorageService.uploadFile(file);
                uploadedFiles.add(Map.of(
                    "url", fileUrl,
                    "originalName", file.getOriginalFilename() != null ? file.getOriginalFilename() : "unknown"
                ));
            } catch (IOException e) {
                errors.add("Failed to upload " + file.getOriginalFilename() + ": " + e.getMessage());
            }
        }
        
        if (uploadedFiles.isEmpty() && !errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to upload any files", "details", errors));
        }
        
        return ResponseEntity.ok(Map.of(
            "files", uploadedFiles,
            "message", "Files uploaded successfully to Supabase Storage",
            "errors", errors
        ));
    }
}

