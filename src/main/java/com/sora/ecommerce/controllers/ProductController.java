package com.sora.ecommerce.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sora.ecommerce.constants.ResponseStatus;
import com.sora.ecommerce.response.ResponseHandler;
import com.sora.ecommerce.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public ResponseEntity<Object> findProductByName(@RequestParam String name) {
        var result = productService.findProductByName(name);
        return ResponseHandler.responseBuilder(HttpStatus.OK, ResponseStatus.SUCCESS, result);
    }

    @GetMapping("/price")
    public ResponseEntity<Object> findProductByPrice(@RequestParam Float min, @RequestParam Float max) {
        var result = productService.findProductByPrice(min, max);
        return ResponseHandler.responseBuilder(HttpStatus.OK, ResponseStatus.SUCCESS, result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findProductById(@PathVariable Integer id) {
        var result = productService.findProductById(id);
        return ResponseHandler.responseBuilder(HttpStatus.OK, ResponseStatus.SUCCESS, result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProductById(@PathVariable Integer id) {
        productService.deleteProductById(id);
        return ResponseHandler.responseBuilder(HttpStatus.OK, ResponseStatus.SUCCESS, null);
    }

}
