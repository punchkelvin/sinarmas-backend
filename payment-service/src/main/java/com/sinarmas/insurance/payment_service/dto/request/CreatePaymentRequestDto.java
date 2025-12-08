package com.sinarmas.insurance.payment_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequestDto {

    @NotNull(message = "Policy Id is required")
    private Long policyId;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;
}
