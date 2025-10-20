package com.erp_project.project.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "supabase")
@Data
public class SupabaseConfig {
    private String url;
    private String key;
    private Jwt jwt = new Jwt();
    
    @Data
    public static class Jwt {
        private String secret;
    }
}

