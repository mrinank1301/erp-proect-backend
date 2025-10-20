package com.erp_project.project.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.erp_project.project.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtService jwtService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                String userId = jwtService.getUserIdFromToken(token);
                String role = jwtService.getRoleFromToken(token);
                String email = jwtService.getEmailFromToken(token);
                
                // Debug logging
                System.out.println("JWT Authentication - User ID: " + userId);
                System.out.println("JWT Authentication - Role: " + role);
                System.out.println("JWT Authentication - Email: " + email);
                
                // Set attributes for use in controllers
                request.setAttribute("userId", userId);
                request.setAttribute("userRole", role);
                request.setAttribute("userEmail", email);
            } catch (Exception e) {
                System.err.println("JWT Validation Error: " + e.getMessage());
                e.printStackTrace();
                // Invalid token - continue without authentication
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\": \"Invalid or expired token\"}");
                return;
            }
        }
        
        filterChain.doFilter(request, response);
    }
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // Don't filter public endpoints
        return path.startsWith("/api/cars") && request.getMethod().equals("GET");
    }
}

