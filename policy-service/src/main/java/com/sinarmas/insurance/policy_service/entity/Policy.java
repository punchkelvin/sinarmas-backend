package com.sinarmas.insurance.policy_service.entity;

import com.sinarmas.insurance.policy_service.enums.PolicyStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String policyNumber;

    private Long customerId;
    private Long productId;

    // Snapshot Data
    private String productName;
    private BigDecimal premiumPaid;
    private BigDecimal coverageAmount;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private PolicyStatus status;
}
