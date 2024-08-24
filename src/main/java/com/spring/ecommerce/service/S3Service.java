package com.spring.ecommerce.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class S3Service {

    private final AmazonS3 amazonS3;

    private final String bucketName;

    ObjectMetadata metadata = new ObjectMetadata();




    public S3Service(AmazonS3 amazonS3, @Value("${aws.s3.bucket.name}") String bucketName) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;

    }


    public List<String> upload(Long id, List<File> files) throws IOException {
        return files.stream().map(file -> {
            try {

                String fileKey = "product/" + id + "/" + file.getName().replaceAll("\\s+", "");

//                String fileKey = "product/" + id + "/" + file.getName();
                amazonS3.putObject(new PutObjectRequest(bucketName, fileKey, file).withCannedAcl(CannedAccessControlList.PublicRead));


                String fileUrl = amazonS3.getUrl(bucketName, fileKey).toString();
                return fileUrl;
            } catch (Exception e) {
                throw new RuntimeException("Error uploading file: " + file.getName(), e);
            }
        }).collect(Collectors.toList());
    }



    public String uploadBanner( MultipartFile file) throws IOException {
        String contenttype = URLConnection.guessContentTypeFromName(file.getOriginalFilename());
        metadata.setContentLength(file.getSize());

        if (contenttype != null){
            metadata.setContentType(contenttype);
        }
        else {
            metadata.setContentType("application/octet-stream");
        }
        String fileKey = "banner/" + file.getOriginalFilename();
        try {
            amazonS3.putObject(new PutObjectRequest(bucketName, fileKey, file.getInputStream(), metadata));
            return amazonS3.getUrl(bucketName, fileKey).toString();
        }catch (Exception e){
            throw new RuntimeException("Error uploading file: " + file.getOriginalFilename(), e);
        }

}



    public String getFileKey(String url){
        int index = url.indexOf(".com/") + 5;

        return url.substring(index);
    }


    public void remove(String url) {
        amazonS3.deleteObject(bucketName, getFileKey(url));

    }
}
