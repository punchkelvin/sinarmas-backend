package com.sinarmas.insurance.customer_service.controller;

import com.sinarmas.insurance.common_lib.constant.CommonConstants;
import com.sinarmas.insurance.common_lib.dto.response.BaseResponseDto;
import com.sinarmas.insurance.customer_service.dto.request.AddCustomerRequestDto;
import com.sinarmas.insurance.customer_service.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto> getCustomer(@PathVariable Long id){
        BaseResponseDto baseResponseDto = customerService.getCustomer(id);

        if(baseResponseDto.getStatusCode().equals(CommonConstants.FAIL_CODE)) {
            return ResponseEntity.badRequest().body(baseResponseDto);
        }

        return ResponseEntity.ok().body(baseResponseDto);
    }


    @PostMapping
    public ResponseEntity<BaseResponseDto> addUser(@Valid @RequestBody AddCustomerRequestDto addCustomerRequestDto){
        BaseResponseDto baseResponseDto = customerService.addCustomer(addCustomerRequestDto);

        if(baseResponseDto.getStatusCode().equals(CommonConstants.FAIL_CODE)) {
            return ResponseEntity.badRequest().body(baseResponseDto);
        }

        return ResponseEntity.ok().body(baseResponseDto);
    }
}
