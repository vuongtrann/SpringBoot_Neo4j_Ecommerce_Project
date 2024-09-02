
package com.spring.ecommerce.controller;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.spring.ecommerce.persistence.model.Banners;
import com.spring.ecommerce.service.BannerService;
import com.spring.ecommerce.service.S3Service;
import com.spring.ecommerce.util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;


    @GetMapping("")
    public ResponseEntity<List<Banners>> getAll() throws NullPointerException, AmazonS3Exception  {
            try {
                return new ResponseEntity<>(bannerService.findAll(), HttpStatus.OK);
            }catch (Exception e){
                e.printStackTrace();
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Banners> getById(@PathVariable("id") Long id) throws NullPointerException, AmazonS3Exception {
        try{
            Optional<Banners> banners = bannerService.findById(id);
            if (banners.isPresent()) {
                return new ResponseEntity<>(banners.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }






    @PostMapping(value = "/add", consumes = "multipart/form-data")
    public ResponseEntity<List<String>> add (
            @RequestPart("file") List<MultipartFile> file) throws IOException, AmazonS3Exception {
        try{
            List<String> bannerURLs= bannerService.saveMultil(file);
            return new ResponseEntity<>(bannerURLs, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }




    @PutMapping(value = "/{id}/update", consumes = "multipart/form-data")
    public ResponseEntity<Banners> update (@RequestPart("file") MultipartFile file, @PathVariable("id") Long id) throws IOException,AmazonS3Exception, NullPointerException {
        try {
            return new ResponseEntity<>(bannerService.update(file, id), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> delete (@PathVariable("id") Long id) throws  NullPointerException,SdkClientException {
        try {
            bannerService.delete(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PutMapping("/status")
    public ResponseEntity<Banners> updateStatus(@PathVariable("id") Long id) throws NullPointerException {
       try {
           return new ResponseEntity<>(bannerService.updateStatus(id), HttpStatus.OK);
       }catch (Exception e){
           e.printStackTrace();
           return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }








}
