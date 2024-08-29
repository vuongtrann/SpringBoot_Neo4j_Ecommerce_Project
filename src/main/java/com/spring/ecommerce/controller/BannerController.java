
package com.spring.ecommerce.controller;

import com.spring.ecommerce.persistence.model.Banners;
import com.spring.ecommerce.service.BannerService;
import com.spring.ecommerce.service.S3Service;
import com.spring.ecommerce.util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @Autowired
    S3Service s3Service;


    @GetMapping("")
    public RestResponse getAll() {
        try{
            List<String> bannersURL = bannerService.findAll();
            return RestResponse.builder(bannersURL).message("Success").build();

        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.builder().message("Error").build();
        }

    }


    @GetMapping(value = "/{id}")
    public RestResponse getById(@PathVariable("id") Long id) {
        try {
            Optional<Banners> banners = bannerService.findById(id);
            if (banners.isPresent()) {
                return RestResponse.builder(banners.get()).message("Success").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.builder().message("Error").build();

        }

        return RestResponse.builder(null).message("Error").build();
    }






    @PostMapping(value = "/add", consumes = "multipart/form-data")
    public RestResponse add (
            @RequestPart("file") List<MultipartFile> file) throws IOException {
        try{
            bannerService.saveMultil(file);
            return RestResponse.builder().message("Image added").build();
        }catch (Exception e) {
            return RestResponse.builder().message("Error").build();
        }

    }




    @PutMapping(value = "/{id}/update", consumes = "multipart/form-data")
    public RestResponse update (@RequestPart("file") MultipartFile file, @PathVariable("id") Long id) throws IOException {

        return RestResponse.builder(bannerService.update(file, id)).message("Success").build();
    }

    @DeleteMapping("/{id}/delete")
    public RestResponse delete (@PathVariable("id") Long id){
        bannerService.delete(id);
        return RestResponse.builder().message("Success").build();
    }











}
