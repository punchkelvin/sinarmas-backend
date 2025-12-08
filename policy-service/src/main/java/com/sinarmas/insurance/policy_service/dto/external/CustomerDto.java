package com.sinarmas.insurance.policy_service.dto.external;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private Long id;

    private String fullName;
    private String nik;
    private String email;
    private String phoneNumber;
    private String address;
    private LocalDate dob;
}
