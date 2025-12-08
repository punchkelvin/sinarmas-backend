package com.sinarmas.insurance.policy_service.service;

import com.sinarmas.insurance.common_lib.constant.CommonConstants;
import com.sinarmas.insurance.common_lib.dto.response.BaseResponseDto;
import com.sinarmas.insurance.policy_service.client.InsuranceClient;
import com.sinarmas.insurance.policy_service.dto.external.CustomerDto;
import com.sinarmas.insurance.policy_service.dto.external.ProductDto;
import com.sinarmas.insurance.policy_service.dto.request.CreatePolicyRequestDto;
import com.sinarmas.insurance.policy_service.entity.Policy;
import com.sinarmas.insurance.policy_service.enums.PolicyStatus;
import com.sinarmas.insurance.policy_service.repository.PolicyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PolicyService {
    private final PolicyRepository policyRepository;
    private final InsuranceClient insuranceClient;


    public BaseResponseDto getPolicy(Long id) {
        BaseResponseDto policyResponseDto = BaseResponseDto
                .builder()
                .statusCode(CommonConstants.SUCCESS_CODE)
                .statusMessage(CommonConstants.SUCCESS_MESSAGE)
                .message(CommonConstants.SUCCESS_MESSAGE)
                .createdDate(LocalDateTime.now())
                .build();

        try {
            Policy policy = policyRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Policy Not Found"));

            policyResponseDto.setData(policy);


        } catch (Exception e) {
            policyResponseDto.setMessage(e.getMessage());
            policyResponseDto.setStatusMessage(CommonConstants.FAIL_MESSAGE);
            policyResponseDto.setStatusCode(CommonConstants.FAIL_CODE);
        }

        return policyResponseDto;
    }


    public BaseResponseDto createPolicy(CreatePolicyRequestDto createPolicyRequestDto) {
        BaseResponseDto policyResponseDto = BaseResponseDto
                .builder()
                .statusCode(CommonConstants.SUCCESS_CODE)
                .statusMessage(CommonConstants.SUCCESS_MESSAGE)
                .message(CommonConstants.SUCCESS_MESSAGE)
                .createdDate(LocalDateTime.now())
                .build();

        try {
            //customer service call
            CustomerDto customer = insuranceClient.getCustomerById(createPolicyRequestDto.getCustomerId());

            //product service call
            ProductDto product = insuranceClient.getProductById(createPolicyRequestDto.getProductId());


            // create policy
            Policy newPolicy = Policy.builder()
                    .policyNumber("POL-" + System.currentTimeMillis())
                    .customerId(createPolicyRequestDto.getCustomerId())
                    .productId(createPolicyRequestDto.getProductId())
                    .productName(product.getName())
                    .premiumPaid(product.getFinalPremium())
                    .coverageAmount(product.getMaxCoverage())
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now().plusYears(1))
                    .status(PolicyStatus.PENDING_PAYMENT)
                    .build();

            policyRepository.save(newPolicy);
            policyResponseDto.setData(newPolicy);

        } catch (Exception e) {
            policyResponseDto.setMessage(e.getMessage());
            policyResponseDto.setStatusMessage(CommonConstants.FAIL_MESSAGE);
            policyResponseDto.setStatusCode(CommonConstants.FAIL_CODE);
        }
        return policyResponseDto;
    }
}
