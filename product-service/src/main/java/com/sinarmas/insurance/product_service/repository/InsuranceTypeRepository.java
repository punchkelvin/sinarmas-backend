package com.sinarmas.insurance.product_service.repository;

import com.sinarmas.insurance.product_service.entity.InsuranceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceTypeRepository extends JpaRepository<InsuranceType, Long> {
}
