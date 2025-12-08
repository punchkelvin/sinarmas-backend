package com.sinarmas.insurance.claim_service.service;

import com.sinarmas.insurance.claim_service.client.PolicyClient;
import com.sinarmas.insurance.claim_service.dto.external.PolicyDto;
import com.sinarmas.insurance.claim_service.dto.request.SubmitClaimRequestDto;
import com.sinarmas.insurance.claim_service.entity.Claim;
import com.sinarmas.insurance.claim_service.enums.ClaimStatus;
import com.sinarmas.insurance.claim_service.repository.ClaimRepository;
import com.sinarmas.insurance.common_lib.constant.CommonConstants;
import com.sinarmas.insurance.common_lib.dto.response.BaseResponseDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ClaimService {
    private final ClaimRepository claimRepository;
    private final PolicyClient policyClient;


    public BaseResponseDto getClaimInfo(Long id) {
        BaseResponseDto claimResponseDto = BaseResponseDto
                .builder()
                .statusCode(CommonConstants.SUCCESS_CODE)
                .statusMessage(CommonConstants.SUCCESS_MESSAGE)
                .message(CommonConstants.SUCCESS_MESSAGE)
                .createdDate(LocalDateTime.now())
                .build();

        try {
            Claim product = claimRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Claim Not Found"));

            claimResponseDto.setData(product);

        } catch (Exception e) {
            claimResponseDto.setMessage(e.getMessage());
            claimResponseDto.setStatusMessage(CommonConstants.FAIL_MESSAGE);
            claimResponseDto.setStatusCode(CommonConstants.FAIL_CODE);
        }

        return claimResponseDto;


    }

    public BaseResponseDto submitClaim(@Valid SubmitClaimRequestDto submitClaimRequestDto) {
        BaseResponseDto claimResponseDto = BaseResponseDto
                .builder()
                .statusCode(CommonConstants.SUCCESS_CODE)
                .statusMessage(CommonConstants.SUCCESS_MESSAGE)
                .message(CommonConstants.SUCCESS_MESSAGE)
                .createdDate(LocalDateTime.now())
                .build();

        try {
            PolicyDto policy = policyClient.getPolicyById(submitClaimRequestDto.getPolicyId());

            if (policy == null) {
                throw new RuntimeException("Policy ID " + submitClaimRequestDto.getPolicyId() + " not found.");
            }

            Claim claim = Claim.builder()
                    .policyId(submitClaimRequestDto.getPolicyId())
                    .claimAmount(submitClaimRequestDto.getClaimAmount())
                    .incidentDate(submitClaimRequestDto.getIncidentDateTime())
                    .description(submitClaimRequestDto.getDescription())
                    .build();

            //policy must be active
            if (!CommonConstants.ACTIVE.equals(policy.getStatus())) {
                claim.setStatus(ClaimStatus.REJECTED);
                claim.setRejectionReason("Policy is not ACTIVE. Current status: " + policy.getStatus());
                claimRepository.save(claim);
                claimResponseDto.setData(claim);
                return claimResponseDto;
            }

            //Prevent Identical Duplicate Submissions
            if (claimRepository.existsByPolicyIdAndClaimAmountAndIncidentDate(
                    submitClaimRequestDto.getPolicyId(), submitClaimRequestDto.getClaimAmount(), submitClaimRequestDto.getIncidentDateTime())) {
                throw new RuntimeException("An identical claim for this incident has already been submitted.");
            }


            //claim amount <= coverage limit
            if (submitClaimRequestDto.getClaimAmount().compareTo(policy.getCoverageAmount()) > 0) {
                claim.setStatus(ClaimStatus.REJECTED);
                claim.setRejectionReason("Claim amount exceeds coverage limit of " + policy.getCoverageAmount());
                claimRepository.save(claim);
                claimResponseDto.setData(claim);
                return claimResponseDto;
            }

            claim.setStatus(ClaimStatus.SUBMITTED);
            claimRepository.save(claim);

            claimResponseDto.setData(claim);

        } catch (Exception e) {
            claimResponseDto.setMessage(e.getMessage());
            claimResponseDto.setStatusMessage(CommonConstants.FAIL_MESSAGE);
            claimResponseDto.setStatusCode(CommonConstants.FAIL_CODE);
        }

        return claimResponseDto;
    }
}
