package com.sinarmas.insurance.claim_service.entity;

import com.sinarmas.insurance.claim_service.enums.ClaimStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long policyId;
    private BigDecimal claimAmount;
    private LocalDateTime incidentDate;

    private String description;

    @Enumerated(EnumType.STRING)
    private ClaimStatus status;

    private String rejectionReason;

}
