package com.sinarmas.insurance.payment_service.repository;

import com.sinarmas.insurance.payment_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
