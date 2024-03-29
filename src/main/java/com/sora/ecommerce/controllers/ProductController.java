package com.sora.ecommerce.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sora.ecommerce.annotations.ValidId;
import com.sora.ecommerce.constants.ResponseStatus;
import com.sora.ecommerce.models.requests.CreateProductPayload;
import com.sora.ecommerce.response.ResponseHandler;
import com.sora.ecommerce.services.ProductService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    public ResponseEntity<Object> findProductById(@PathVariable @ValidId Integer id) {
        var result = productService.findProductById(id);
        return ResponseHandler.responseBuilder(HttpStatus.OK, ResponseStatus.SUCCESS, result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProductById(@PathVariable @ValidId Integer id) {
        productService.deleteProductById(id);
        return ResponseHandler.responseBuilder(HttpStatus.OK, ResponseStatus.SUCCESS, null);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createProduct(@Valid @RequestPart("details") CreateProductPayload payload,
            @RequestPart("files") MultipartFile[] files) {
        Integer result = productService.createProduct(payload, files);
        return ResponseHandler.responseBuilder(HttpStatus.CREATED, ResponseStatus.SUCCESS, result);
    }

}
