package com.sinarmas.insurance.product_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductRequestDto {

    @NotBlank(message = "Product name cannot be empty")
    private String name;

    @NotNull(message = "Base Premium is required")
    @Positive(message = "Premium must be greater than 0")
    private BigDecimal basePremium;

    @NotNull(message = "Coverage Amount is required")
    @Positive(message = "Coverage must be greater than 0")
    private BigDecimal coverageAmount;

    @NotBlank(message = "Currency is required")
    private String currency;

    @NotNull(message = "Insurance Type is required")
    private Long insuranceType;
}
