
package com.spring.ecommerce.controller;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.spring.ecommerce.persistence.model.Banners;
import com.spring.ecommerce.service.BannerService;
import com.spring.ecommerce.service.S3Service;
import com.spring.ecommerce.util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
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
    public RestResponse getAll() throws NullPointerException, AmazonS3Exception  {
            List<Banners> bannerURLs = bannerService.findAll();
            return RestResponse.builder(bannerURLs).message("Success").build();
    }


    @GetMapping(value = "/{id}")
    public RestResponse getById(@PathVariable("id") Long id) throws NullPointerException, AmazonS3Exception {
        Optional<Banners> banners = bannerService.findById(id);
        if (banners.isPresent()) {
            return RestResponse.builder(banners.get()).message("Success").build();
        }else
            return RestResponse.builder().message("Error").build();
    }






    @PostMapping(value = "/add", consumes = "multipart/form-data")
    public RestResponse add (
            @RequestPart("file") List<MultipartFile> file) throws IOException, AmazonS3Exception {
        try{
            List<String> bannerURLs= bannerService.saveMultil(file);
            return RestResponse.builder(bannerURLs).message("Success").build();
        }catch (Exception e){
            e.printStackTrace();
            return RestResponse.builder().message("Error").build();
        }

    }




    @PutMapping(value = "/{id}/update", consumes = "multipart/form-data")
    public RestResponse update (@RequestPart("file") MultipartFile file, @PathVariable("id") Long id) throws IOException, NullPointerException {
        return RestResponse.builder(bannerService.update(file, id)).message("Success").build();
    }



    @DeleteMapping("/{id}/delete")
    public RestResponse delete (@PathVariable("id") Long id) throws  NullPointerException,SdkClientException {
        bannerService.delete(id);
        return RestResponse.builder().message("Success").build();
    }



    @PutMapping("/status")
    public RestResponse updateStatus(@PathVariable("id") Long id) throws NullPointerException {
        bannerService.updateStatus(id);
        return RestResponse.builder().message("Success").build(); }








}
