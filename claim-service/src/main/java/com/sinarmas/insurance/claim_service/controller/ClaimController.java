package com.sinarmas.insurance.claim_service.controller;

import com.sinarmas.insurance.claim_service.dto.request.SubmitClaimRequestDto;
import com.sinarmas.insurance.claim_service.service.ClaimService;
import com.sinarmas.insurance.common_lib.constant.CommonConstants;
import com.sinarmas.insurance.common_lib.dto.response.BaseResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/claim")
@RequiredArgsConstructor
public class ClaimController {
    private final ClaimService claimService;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto> getClaimInfo(@PathVariable Long id){
        BaseResponseDto baseResponseDto = claimService.getClaimInfo(id);

        if(baseResponseDto.getStatusCode().equals(CommonConstants.FAIL_CODE)) {
            return ResponseEntity.badRequest().body(baseResponseDto);
        }

        return ResponseEntity.ok().body(baseResponseDto);
    }


    @PostMapping
    public ResponseEntity<BaseResponseDto> submitClaim(@Valid @RequestBody SubmitClaimRequestDto submitClaimRequestDto){
        BaseResponseDto baseResponseDto = claimService.submitClaim(submitClaimRequestDto);

        if(baseResponseDto.getStatusCode().equals(CommonConstants.FAIL_CODE)) {
            return ResponseEntity.badRequest().body(baseResponseDto);
        }

        return ResponseEntity.ok().body(baseResponseDto);
    }
}
