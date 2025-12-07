package com.sinarmas.insurance.product_service.repository;

import com.sinarmas.insurance.product_service.entity.InsuranceProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceProductRepository extends JpaRepository<InsuranceProduct, Long> {
    boolean existsByName(String name);

}
