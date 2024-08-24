package com.spring.ecommerce.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
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


    public S3Service(AmazonS3 amazonS3, @Value("${aws.s3.bucket.name}") String bucketName) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
    }


    public List<String> upload(Long id, List<File> files) throws IOException {
        return files.stream().map(file -> {
            try {

                String fileKey = "product/" + id + "/" + file.getName();
                amazonS3.putObject(new PutObjectRequest(bucketName, fileKey, file).withCannedAcl(CannedAccessControlList.PublicRead));
                String fileUrl = amazonS3.getUrl(bucketName, fileKey).toString();
                return fileUrl;
            } catch (Exception e) {
                throw new RuntimeException("Error uploading file: " + file.getName(), e);
            }
        }).collect(Collectors.toList());
    }

    public void deleteImagesByUrls(List<String> imageUrls) {
        for (String imageUrl : imageUrls) {
            String objectKey = getFileUrl(imageUrl);
            if (objectKey != null) {
                amazonS3.deleteObject(new DeleteObjectRequest(bucketName, objectKey));
            }
        }
    }
    public String getFileUrl(String fileName) {
        int index = fileName.indexOf(".com/")+5;
        return fileName.substring(index);
    }
}
