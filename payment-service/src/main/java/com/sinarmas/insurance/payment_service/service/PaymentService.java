package com.sinarmas.insurance.payment_service.service;

import com.sinarmas.insurance.common_lib.constant.CommonConstants;
import com.sinarmas.insurance.common_lib.dto.response.BaseResponseDto;
import com.sinarmas.insurance.payment_service.client.PolicyClient;
import com.sinarmas.insurance.payment_service.dto.external.PolicyDto;
import com.sinarmas.insurance.payment_service.dto.request.CreatePaymentRequestDto;
import com.sinarmas.insurance.payment_service.entity.Payment;
import com.sinarmas.insurance.payment_service.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PolicyClient policyClient;

    public BaseResponseDto getPaymentReceipt(Long id) {
        BaseResponseDto paymentResponseDto = BaseResponseDto
                .builder()
                .statusCode(CommonConstants.SUCCESS_CODE)
                .statusMessage(CommonConstants.SUCCESS_MESSAGE)
                .message(CommonConstants.SUCCESS_MESSAGE)
                .createdDate(LocalDateTime.now())
                .build();

        try {
            Payment payment = paymentRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Payment Receipt Not Found"));

            paymentResponseDto.setData(payment);


        } catch (Exception e) {
            paymentResponseDto.setMessage(e.getMessage());
            paymentResponseDto.setStatusMessage(CommonConstants.FAIL_MESSAGE);
            paymentResponseDto.setStatusCode(CommonConstants.FAIL_CODE);
        }
        return paymentResponseDto;
    }

    @Transactional
    public BaseResponseDto processPayment(CreatePaymentRequestDto createPaymentRequestDto) {
        BaseResponseDto policyResponseDto = BaseResponseDto
                .builder()
                .statusCode(CommonConstants.SUCCESS_CODE)
                .statusMessage(CommonConstants.SUCCESS_MESSAGE)
                .message(CommonConstants.SUCCESS_MESSAGE)
                .createdDate(LocalDateTime.now())
                .build();

        try {
            PolicyDto policy = policyClient.getPolicyById(createPaymentRequestDto.getPolicyId());

            if (createPaymentRequestDto.getAmount().compareTo(policy.getPremiumPaid()) < 0) {
                throw new RuntimeException("Insufficient amount. Required: " + policy.getPremiumPaid());
            }

            Payment payment = Payment.builder()
                    .policyId(createPaymentRequestDto.getPolicyId())
                    .amount(createPaymentRequestDto.getAmount())
                    .paymentMethod(CommonConstants.BANK_TRANSFER)
                    .status(CommonConstants.SUCCESS_MESSAGE)
                    .build();

            paymentRepository.save(payment);

            policyClient.activatePolicy(createPaymentRequestDto.getPolicyId());

            policyResponseDto.setData(payment);

        } catch (Exception e) {
            policyResponseDto.setMessage(e.getMessage());
            policyResponseDto.setStatusMessage(CommonConstants.FAIL_MESSAGE);
            policyResponseDto.setStatusCode(CommonConstants.FAIL_CODE);
        }
        return policyResponseDto;
    }
}
