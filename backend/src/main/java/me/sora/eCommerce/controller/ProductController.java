package me.sora.eCommerce.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import me.sora.eCommerce.constant.ErrorConstant;
import me.sora.eCommerce.controller.advice.CustomException;
import me.sora.eCommerce.dto.CommonResponse;
import me.sora.eCommerce.dto.Product.CreateProductRequest;
import me.sora.eCommerce.dto.Product.CreateProductResponse;
import me.sora.eCommerce.dto.Product.GetProductResponse;
import me.sora.eCommerce.service.ProductService;
import me.sora.eCommerce.util.AuthUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
    public ResponseEntity<CommonResponse<CreateProductResponse>> createProduct(
            @Valid @RequestBody CreateProductRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        if (!AuthUtils.hasAdminPermission(userDetails)) {
            throw new CustomException(ErrorConstant.PERMISSION_NOT_ALLOW, HttpStatus.UNAUTHORIZED);
        }

        var response = productService.createProduct(request, userDetails.getUsername());
        var productId = response.getId();
        return ResponseEntity
                .created(URI.create(productId))
                .body(CommonResponse.of(SUCCESS, response));
    }

}
