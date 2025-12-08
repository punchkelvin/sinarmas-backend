package com.sinarmas.insurance.policy_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePolicyRequestDto {

    @NotNull(message = "Customer Id is required")
    private Long customerId;

    @NotNull(message = "Product Id is required")
    private Long productId;

}
