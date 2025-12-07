package com.sinarmas.insurance.product_service.controller;

import com.sinarmas.insurance.common_lib.constant.CommonConstants;
import com.sinarmas.insurance.common_lib.dto.response.BaseResponseDto;
import com.sinarmas.insurance.product_service.dto.request.AddProductRequestDto;
import com.sinarmas.insurance.product_service.dto.request.UpdateProductRequestDto;
import com.sinarmas.insurance.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto> getProduct(@PathVariable Long id){
        BaseResponseDto baseResponseDto = productService.getProduct(id);

        if(baseResponseDto.getStatusCode().equals(CommonConstants.FAIL_CODE)) {
            return ResponseEntity.badRequest().body(baseResponseDto);
        }

        return ResponseEntity.ok().body(baseResponseDto);
    }

    @GetMapping
    public ResponseEntity<BaseResponseDto> getAllProduct(){
        BaseResponseDto baseResponseDto = productService.getAllProducts();

        if(baseResponseDto.getStatusCode().equals(CommonConstants.FAIL_CODE)) {
            return ResponseEntity.badRequest().body(baseResponseDto);
        }

        return ResponseEntity.ok().body(baseResponseDto);
    }

    @PostMapping
    public ResponseEntity<BaseResponseDto> addProduct(@Valid @RequestBody AddProductRequestDto addProductRequestDto){
        BaseResponseDto baseResponseDto = productService.addProduct(addProductRequestDto);

        if(baseResponseDto.getStatusCode().equals(CommonConstants.FAIL_CODE)) {
            return ResponseEntity.badRequest().body(baseResponseDto);
        }

        return ResponseEntity.ok().body(baseResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseDto> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductRequestDto updateProductRequestDto){
        BaseResponseDto baseResponseDto = productService.updateProduct(id, updateProductRequestDto);

        if(baseResponseDto.getStatusCode().equals(CommonConstants.FAIL_CODE)) {
            return ResponseEntity.badRequest().body(baseResponseDto);
        }

        return ResponseEntity.ok().body(baseResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDto> deleteProduct(@PathVariable Long id){
        BaseResponseDto baseResponseDto = productService.deleteProduct(id);

        if(baseResponseDto.getStatusCode().equals(CommonConstants.FAIL_CODE)) {
            return ResponseEntity.badRequest().body(baseResponseDto);
        }

        return ResponseEntity.ok().body(baseResponseDto);
    }


}
