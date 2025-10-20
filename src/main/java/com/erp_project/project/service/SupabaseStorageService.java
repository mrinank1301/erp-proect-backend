package com.erp_project.project.service;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.erp_project.project.config.SupabaseConfig;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupabaseStorageService {
    
    private final SupabaseConfig supabaseConfig;
    private final RestTemplate restTemplate = new RestTemplate();
    
    @Value("${supabase.storage.bucket:car-images}")
    private String bucketName;
    
    /**
     * Upload a file to Supabase Storage
     * @param file The file to upload
     * @return The public URL of the uploaded file
     * @throws IOException if upload fails
     */
    public String uploadFile(MultipartFile file) throws IOException {
        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String filename = UUID.randomUUID().toString() + extension;
        
        // Create file path (organize by date folder for better management)
        String folder = java.time.LocalDate.now().toString();
        String filePath = folder + "/" + filename;
        
        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(file.getContentType()));
        headers.set("Authorization", "Bearer " + supabaseConfig.getKey());
        headers.set("apikey", supabaseConfig.getKey());
        
        // Upload URL
        String uploadUrl = String.format("%s/storage/v1/object/%s/%s", 
            supabaseConfig.getUrl(), bucketName, filePath);
        
        // Create request
        HttpEntity<byte[]> requestEntity = new HttpEntity<>(file.getBytes(), headers);
        
        try {
            // Upload file
            ResponseEntity<Map> response = restTemplate.exchange(
                uploadUrl,
                HttpMethod.POST,
                requestEntity,
                Map.class
            );
            
            // Return public URL
            String publicUrl = String.format("%s/storage/v1/object/public/%s/%s",
                supabaseConfig.getUrl(), bucketName, filePath);
            
            System.out.println("File uploaded successfully to: " + publicUrl);
            return publicUrl;
            
        } catch (Exception e) {
            System.err.println("Error uploading file to Supabase: " + e.getMessage());
            e.printStackTrace();
            throw new IOException("Failed to upload file to Supabase Storage: " + e.getMessage(), e);
        }
    }
    
    /**
     * Delete a file from Supabase Storage
     * @param fileUrl The public URL of the file to delete
     * @return true if deletion was successful
     */
    public boolean deleteFile(String fileUrl) {
        try {
            // Extract file path from URL
            String[] urlParts = fileUrl.split("/storage/v1/object/public/" + bucketName + "/");
            if (urlParts.length < 2) {
                System.err.println("Invalid file URL format: " + fileUrl);
                return false;
            }
            String filePath = urlParts[1];
            
            // Prepare headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + supabaseConfig.getKey());
            headers.set("apikey", supabaseConfig.getKey());
            
            // Delete URL
            String deleteUrl = String.format("%s/storage/v1/object/%s/%s",
                supabaseConfig.getUrl(), bucketName, filePath);
            
            // Create request
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);
            
            // Delete file
            ResponseEntity<Map> response = restTemplate.exchange(
                deleteUrl,
                HttpMethod.DELETE,
                requestEntity,
                Map.class
            );
            
            System.out.println("File deleted successfully from Supabase Storage");
            return response.getStatusCode().is2xxSuccessful();
            
        } catch (Exception e) {
            System.err.println("Error deleting file from Supabase: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get the public URL for a file
     * @param filePath The path of the file in the bucket
     * @return The public URL
     */
    public String getPublicUrl(String filePath) {
        return String.format("%s/storage/v1/object/public/%s/%s",
            supabaseConfig.getUrl(), bucketName, filePath);
    }
}

