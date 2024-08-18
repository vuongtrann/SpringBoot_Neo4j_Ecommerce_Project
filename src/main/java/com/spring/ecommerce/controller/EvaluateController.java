package com.spring.ecommerce.controller;

import com.spring.ecommerce.persistence.model.Review;
import com.spring.ecommerce.service.ReviewService;
import com.spring.ecommerce.util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2")
//@RequestMapping("/api/v2/evaluate")
public class EvaluateController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/evaluate")
    public RestResponse getAllEvaluates() {
        try {
            return RestResponse.builder(reviewService.findAll()).message("success").build();
        }catch (Exception e){
            return RestResponse.builder(null).message("Error:" + e.getMessage()).build();
        }
    }

    @GetMapping("/evaluate/{id}")
    public RestResponse getEvaluateById(@PathVariable("id") Long id) {
        try {
            return RestResponse.builder(reviewService.findById(id)).message("success").build();
        }
        catch (Exception e) {
            return RestResponse.builder(null).message("Error:" + e.getMessage()).build();
        }
    }


    @PostMapping("/product/{proId}/evaluate")
    public RestResponse addEvaluate(@RequestBody Review review, @PathVariable("proId") Long proId) {
        try{
            return RestResponse.builder(reviewService.save(review, proId)).message("success").build();
        }
        catch (Exception e) {
            return RestResponse.builder().message("Error:" + e.getMessage()).build();
        }

    }


    @PutMapping("evaluate/{id}/update")
    public RestResponse updateEvaluate(@PathVariable("id") Long id, @RequestBody Review review) {
      try{
          return RestResponse.builder(reviewService.update(review, id)).build();
      } catch (Exception e){
          return RestResponse.builder().message("Error:" + e.getMessage()).build();
      }
    }

    @GetMapping("/{idProduct}/product")
    public RestResponse getEvaluateProductById(@PathVariable("idProduct") Long idProduct) {
        try{
            return RestResponse.builder(reviewService.findByProductId(idProduct)).message("success").build();
        }catch (Exception e) {
            return RestResponse.builder().message("Error:" + e.getMessage()).build();
        }
    }


}
