package com.sinarmas.insurance.claim_service.repository;

import com.sinarmas.insurance.claim_service.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
    boolean existsByPolicyIdAndClaimAmountAndIncidentDate(Long policyId, BigDecimal amount, LocalDateTime date);
}
