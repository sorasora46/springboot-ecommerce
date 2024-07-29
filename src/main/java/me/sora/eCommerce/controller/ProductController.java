package me.sora.eCommerce.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import me.sora.eCommerce.dto.CommonResponse;
import me.sora.eCommerce.dto.Product.CreateProductRequest;
import me.sora.eCommerce.dto.Product.GetProductResponse;
import me.sora.eCommerce.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static me.sora.eCommerce.constant.ApiConstant.ApiStatus.SUCCESS;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<GetProductResponse>> getProductById(@NotEmpty @PathVariable String id) {
        var response = productService.getProductById(id);
        return ResponseEntity.ok().body(CommonResponse.of(SUCCESS, response));
    }

    @PostMapping()
    public ResponseEntity<CommonResponse<Object>> createProduct(@Valid @RequestBody CreateProductRequest request) {
    }

}
