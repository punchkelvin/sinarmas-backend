package com.sinarmas.insurance.customer_service.repository;

import com.sinarmas.insurance.customer_service.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByNik(String nik);
    boolean existsByEmail(String email);
}
