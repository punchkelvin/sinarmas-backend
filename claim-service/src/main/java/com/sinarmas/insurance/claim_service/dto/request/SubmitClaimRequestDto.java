package com.sinarmas.insurance.claim_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmitClaimRequestDto {

    @NotNull(message = "Policy ID is required")
    private Long policyId;

    @NotNull(message = "Claim amount is required")
    private BigDecimal claimAmount;

    @NotNull(message = "Incident Datetime is required")
    private LocalDateTime incidentDateTime;

    @NotBlank(message = "Description is required")
    private String description;
}
