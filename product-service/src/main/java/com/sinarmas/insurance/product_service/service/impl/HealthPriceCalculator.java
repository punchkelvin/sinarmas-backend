package com.sinarmas.insurance.product_service.service.impl;

import com.sinarmas.insurance.common_lib.constant.CommonConstants;
import com.sinarmas.insurance.product_service.service.PriceCalculatorStrategy;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class HealthPriceCalculator implements PriceCalculatorStrategy {

    @Override
    public BigDecimal calculateFinalPrice(BigDecimal basePremium) {
        return basePremium.add(basePremium.multiply(CommonConstants.ADMIN_FEE_5_PERCENT));
    }

    @Override
    public String getSupportedType() {
        return CommonConstants.HEALTH;
    }
}
