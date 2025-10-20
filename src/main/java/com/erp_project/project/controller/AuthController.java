package com.erp_project.project.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erp_project.project.dto.AuthResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    @GetMapping("/verify")
    public ResponseEntity<?> verifyToken(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        String role = (String) request.getAttribute("userRole");
        String email = (String) request.getAttribute("userEmail");
        
        if (userId == null) {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Invalid or missing token"));
        }
        
        AuthResponse response = new AuthResponse(userId, email, role, "Token is valid");
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        String role = (String) request.getAttribute("userRole");
        String email = (String) request.getAttribute("userEmail");
        
        if (userId == null) {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Unauthorized"));
        }
        
        return ResponseEntity.ok(Map.of(
            "userId", userId,
            "email", email != null ? email : "",
            "role", role != null ? role : "user"
        ));
    }
}

