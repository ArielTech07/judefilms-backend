package com.ariel.backendspring.backend_springboot_judefilms.services;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.UUID;

@Service
public class R2StorageService {

    @Value("${cloudflare.r2.access-key}")
    private String accessKey;

    @Value("${cloudflare.r2.secret-key}")
    private String secretKey;

    @Value("${cloudflare.r2.endpoint}")
    private String endpoint;

    @Value("${cloudflare.r2.bucket-name}")
    private String bucketName;

    @Value("${cloudflare.r2.public-url}")
    private String publicUrl;

    private AmazonS3 s3Client;

    @PostConstruct
    public void init() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        s3Client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(
                    new AwsClientBuilder.EndpointConfiguration(endpoint, "auto"))
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withPathStyleAccessEnabled(true)
                .build();
    }

    
    public String uploadPdf(MultipartFile file) throws IOException {
        String originalName = file.getOriginalFilename();
        // Nombre único para evitar colisiones
        String key = "contratos/" + UUID.randomUUID() + "_" + originalName;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType("application/pdf");

        s3Client.putObject(new PutObjectRequest(
                bucketName, key, file.getInputStream(), metadata));

        return publicUrl + "/" + key;
    }

    public static record R2UploadResult(String url, String key) {}

    public R2UploadResult uploadImage(MultipartFile file) throws IOException {
        String originalName = file.getOriginalFilename();
        String safeName = (originalName != null)
                ? originalName.trim().replaceAll("\\s+", "_").replaceAll("[^a-zA-Z0-9._-]", "")
                : "imagen.jpg";
        if (safeName.isBlank()) {
            safeName = "imagen.jpg";
        }

        String key = "galeria/" + UUID.randomUUID() + "_" + safeName;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());

        String contentType = file.getContentType();
        if (contentType == null || contentType.isBlank() || contentType.contains(",")) {
            contentType = "image/jpeg";
        }
        metadata.setContentType(contentType);

        s3Client.putObject(new PutObjectRequest(
                bucketName, key, file.getInputStream(), metadata));

        String url = publicUrl + "/" + key;
        return new R2UploadResult(url, key);
    }

    public void deleteFile(String key){
        s3Client.deleteObject(bucketName,key);
    }
}

