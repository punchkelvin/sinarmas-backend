package com.sinarmas.insurance.policy_service.controller;

import com.sinarmas.insurance.common_lib.constant.CommonConstants;
import com.sinarmas.insurance.common_lib.dto.response.BaseResponseDto;
import com.sinarmas.insurance.policy_service.dto.request.CreatePolicyRequestDto;
import com.sinarmas.insurance.policy_service.service.PolicyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/policy")
@RequiredArgsConstructor
public class PolicyController {
    private final PolicyService policyService;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto> getPolicy(@PathVariable Long id){
        BaseResponseDto baseResponseDto = policyService.getPolicy(id);

        if(baseResponseDto.getStatusCode().equals(CommonConstants.FAIL_CODE)) {
            return ResponseEntity.badRequest().body(baseResponseDto);
        }

        return ResponseEntity.ok().body(baseResponseDto);
    }


    @PostMapping
    public ResponseEntity<BaseResponseDto> createPolicy(@Valid @RequestBody CreatePolicyRequestDto createPolicyRequestDto){
        BaseResponseDto baseResponseDto = policyService.createPolicy(createPolicyRequestDto);

        if(baseResponseDto.getStatusCode().equals(CommonConstants.FAIL_CODE)) {
            return ResponseEntity.badRequest().body(baseResponseDto);
        }

        return ResponseEntity.ok().body(baseResponseDto);
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<BaseResponseDto> activatePolicy(@PathVariable Long id) {
        BaseResponseDto baseResponseDto = policyService.activatePolicy(id);

        if(baseResponseDto.getStatusCode().equals(CommonConstants.FAIL_CODE)) {
            return ResponseEntity.badRequest().body(baseResponseDto);
        }

        return ResponseEntity.ok().body(baseResponseDto);

    }

}
