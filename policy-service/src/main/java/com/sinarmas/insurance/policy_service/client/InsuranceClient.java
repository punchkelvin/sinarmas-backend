package com.sinarmas.insurance.policy_service.client;

import com.sinarmas.insurance.common_lib.dto.response.BaseResponseDto;
import com.sinarmas.insurance.policy_service.dto.external.CustomerDto;
import com.sinarmas.insurance.policy_service.dto.external.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class InsuranceClient {
    private final WebClient.Builder webClientBuilder;

    @Value("${service.product.url}")
    private String productServiceUrl;

    @Value("${service.customer.url}")
    private String customerServiceUrl;

    public ProductDto getProductById(Long productId) {
        String url = productServiceUrl + "/api/v1/product/" + productId;

        BaseResponseDto<ProductDto> response = webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<BaseResponseDto<ProductDto>>() {})
                .block();

        if (response == null || response.getData() == null) {
            throw new RuntimeException("Product Service is down or product not found");
        }
        return response.getData();
    }

    public CustomerDto getCustomerById(Long customerId) {
        String url = customerServiceUrl + "/api/v1/customer/" + customerId;

        BaseResponseDto<CustomerDto> response = webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<BaseResponseDto<CustomerDto>>() {})
                .block();

        if (response == null || response.getData() == null) {
            throw new RuntimeException("Customer Service is down or customer not found");
        }
        return response.getData();
    }
}
