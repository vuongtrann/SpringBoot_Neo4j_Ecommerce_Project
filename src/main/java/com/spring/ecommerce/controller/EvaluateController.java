package com.spring.ecommerce.controller;

import com.spring.ecommerce.persistence.model.Evaluate;
import com.spring.ecommerce.service.EvaluateService;
import com.spring.ecommerce.util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/evaluate")
public class EvaluateController {

    @Autowired
    private EvaluateService evaluateService;

    @GetMapping("")
    public RestResponse getAllEvaluates() {
        try {
            return RestResponse.builder(evaluateService.findAll()).message("success").build();
        }catch (Exception e){
            return RestResponse.builder(null).message("Error:" + e.getMessage()).build();
        }
    }

    @GetMapping("/{id}")
    public RestResponse getEvaluateById(@PathVariable("id") Long id) {
        try {
            return RestResponse.builder(evaluateService.findById(id)).message("success").build();
        }
        catch (Exception e) {
            return RestResponse.builder(null).message("Error:" + e.getMessage()).build();
        }
    }


    @PostMapping("/add/{proId}/product/{cusId}/customer")
    public RestResponse addEvaluate(@RequestBody Evaluate evaluate, @PathVariable Long proId, @PathVariable Long cusId) {
        try{
            return RestResponse.builder(evaluateService.save(evaluate, proId, cusId)).message("success").build();
        }
        catch (Exception e) {
            return RestResponse.builder().message("Error:" + e.getMessage()).build();
        }

    }


    @PutMapping("/{id}/update")
    public RestResponse updateEvaluate(@PathVariable("id") Long id, @RequestBody Evaluate evaluate) {
      try{
          return RestResponse.builder(evaluateService.update(evaluate, id)).build();
      } catch (Exception e){
          return RestResponse.builder().message("Error:" + e.getMessage()).build();
      }
    }

    @GetMapping("/{idProduct}/product")
    public RestResponse getEvaluateProductById(@PathVariable("idProduct") Long idProduct) {
        try{
            return RestResponse.builder(evaluateService.findByProductId(idProduct)).message("success").build();
        }catch (Exception e) {
            return RestResponse.builder().message("Error:" + e.getMessage()).build();
        }
    }


}
