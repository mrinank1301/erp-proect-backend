package com.erp_project.project.service;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.erp_project.project.config.SupabaseConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    private final SupabaseConfig supabaseConfig;
    private final SecretKey key;
    
    public JwtService(SupabaseConfig supabaseConfig) {
        this.supabaseConfig = supabaseConfig;
        this.key = Keys.hmacShaKeyFor(
            supabaseConfig.getJwt().getSecret().getBytes(StandardCharsets.UTF_8)
        );
    }
    
    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    public String getUserIdFromToken(String token) {
        Claims claims = validateToken(token);
        return claims.getSubject();
    }
    
    public String getRoleFromToken(String token) {
        Claims claims = validateToken(token);
        
        // Try to get role from user_metadata (Supabase structure)
        Object userMetadata = claims.get("user_metadata");
        if (userMetadata instanceof java.util.Map) {
            @SuppressWarnings("unchecked")
            java.util.Map<String, Object> metadata = (java.util.Map<String, Object>) userMetadata;
            Object role = metadata.get("user_role");
            if (role != null) {
                return role.toString();
            }
        }
        
        // Fallback: try direct claim
        String role = (String) claims.get("user_role");
        if (role != null) {
            return role;
        }
        
        // Default to user
        return "user";
    }
    
    public String getEmailFromToken(String token) {
        Claims claims = validateToken(token);
        return (String) claims.get("email");
    }
}

