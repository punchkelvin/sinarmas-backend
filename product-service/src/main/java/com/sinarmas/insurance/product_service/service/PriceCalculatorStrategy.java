package com.sinarmas.insurance.product_service.service;

import java.math.BigDecimal;

public interface PriceCalculatorStrategy {
    BigDecimal calculateFinalPrice(BigDecimal basePremium);
    String getSupportedType();
}
