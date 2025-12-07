package com.sinarmas.insurance.customer_service.service;

import com.sinarmas.insurance.common_lib.constant.CommonConstants;
import com.sinarmas.insurance.common_lib.dto.response.BaseResponseDto;
import com.sinarmas.insurance.customer_service.dto.request.AddCustomerRequestDto;
import com.sinarmas.insurance.customer_service.entity.Customer;
import com.sinarmas.insurance.customer_service.repository.CustomerRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public BaseResponseDto getCustomer(Long id) {
        BaseResponseDto customerResponseDto = BaseResponseDto
                .builder()
                .statusCode(CommonConstants.SUCCESS_CODE)
                .statusMessage(CommonConstants.SUCCESS_MESSAGE)
                .message(CommonConstants.SUCCESS_MESSAGE)
                .createdDate(LocalDateTime.now())
                .build();

        try {
            Customer customer = customerRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Customer Not Found"));

            customerResponseDto.setData(customer);


        } catch (Exception e) {
            customerResponseDto.setMessage(e.getMessage());
            customerResponseDto.setStatusMessage(CommonConstants.FAIL_MESSAGE);
            customerResponseDto.setStatusCode(CommonConstants.FAIL_CODE);
        }

        return customerResponseDto;
    }

    public BaseResponseDto addCustomer(AddCustomerRequestDto addCustomerRequestDto) {
        BaseResponseDto customerResponseDto = BaseResponseDto
                .builder()
                .statusCode(CommonConstants.SUCCESS_CODE)
                .statusMessage(CommonConstants.SUCCESS_MESSAGE)
                .message(CommonConstants.SUCCESS_MESSAGE)
                .createdDate(LocalDateTime.now())
                .build();

        try {
            if (customerRepository.existsByNik(addCustomerRequestDto.getNik())) {
                throw new EntityExistsException("Customer with NIK " + addCustomerRequestDto.getNik() + " is already registered.");
            }
            if (customerRepository.existsByEmail(addCustomerRequestDto.getEmail())) {
                throw new EntityExistsException("Email " + addCustomerRequestDto.getEmail() + " is already in use.");
            }

            Customer newCustomer = Customer.builder()
                    .nik(addCustomerRequestDto.getNik())
                    .fullName(addCustomerRequestDto.getFullName())
                    .email(addCustomerRequestDto.getEmail())
                    .phoneNumber(addCustomerRequestDto.getPhoneNumber())
                    .address(addCustomerRequestDto.getAddress())
                    .dob(addCustomerRequestDto.getDob())
                    .build();

            customerRepository.save(newCustomer);

            customerResponseDto.setData(newCustomer);


        } catch (Exception e) {
            customerResponseDto.setMessage(e.getMessage());
            customerResponseDto.setStatusMessage(CommonConstants.FAIL_MESSAGE);
            customerResponseDto.setStatusCode(CommonConstants.FAIL_CODE);
        }

        return customerResponseDto;

    }
}
