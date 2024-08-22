package com.spring.ecommerce.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class S3Service {

    private final AmazonS3 amazonS3;

    private final String bucketName;

    private final Map<String, String> cache = new HashMap<>();


    public S3Service(AmazonS3 amazonS3, @Value("${aws.s3.bucket.name}") String bucketName) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
    }


    public List<String> upload(Long id, List<File> files) throws IOException {
        return files.stream().map(file -> {
            try {
                // Kiểm tra cache trước
                String fileKey = "product/" + id + "/" + file.getName();
                if (cache.containsKey(fileKey)) {
                    return cache.get(fileKey);
                }

                // Upload tệp lên S3
                amazonS3.putObject(new PutObjectRequest(bucketName, fileKey, file).withCannedAcl(CannedAccessControlList.PublicRead));

                // Lưu vào cache và trả về URL
                String fileUrl = amazonS3.getUrl(bucketName, fileKey).toString();
                cache.put(fileKey, fileUrl);
                return fileUrl;
            } catch (Exception e) {
                throw new RuntimeException("Error uploading file: " + file.getName(), e);
            }
        }).collect(Collectors.toList());
    }

}
