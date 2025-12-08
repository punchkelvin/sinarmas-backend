package com.sinarmas.insurance.payment_service.controller;

import com.sinarmas.insurance.common_lib.constant.CommonConstants;
import com.sinarmas.insurance.common_lib.dto.response.BaseResponseDto;
import com.sinarmas.insurance.payment_service.dto.request.CreatePaymentRequestDto;
import com.sinarmas.insurance.payment_service.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto> getPayment(@PathVariable Long id){
        BaseResponseDto baseResponseDto = paymentService.getPaymentReceipt(id);

        if(baseResponseDto.getStatusCode().equals(CommonConstants.FAIL_CODE)) {
            return ResponseEntity.badRequest().body(baseResponseDto);
        }

        return ResponseEntity.ok().body(baseResponseDto);
    }


    @PostMapping
    public ResponseEntity<BaseResponseDto> createPayment(@Valid @RequestBody CreatePaymentRequestDto createPaymentRequestDto){
        BaseResponseDto baseResponseDto = paymentService.processPayment(createPaymentRequestDto);

        if(baseResponseDto.getStatusCode().equals(CommonConstants.FAIL_CODE)) {
            return ResponseEntity.badRequest().body(baseResponseDto);
        }

        return ResponseEntity.ok().body(baseResponseDto);
    }
}
