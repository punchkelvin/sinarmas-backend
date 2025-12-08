package com.sinarmas.insurance.policy_service.client;

import com.sinarmas.insurance.common_lib.dto.response.BaseResponseDto;
import com.sinarmas.insurance.policy_service.dto.external.CustomerDto;
import com.sinarmas.insurance.policy_service.dto.external.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class InsuranceClient {
    private final WebClient.Builder webClientBuilder;

    @Value("${service.product.url}")
    private String productServiceUrl;

    @Value("${service.customer.url}")
    private String customerServiceUrl;

    public ProductDto getProductById(Long productId) {
        String url = productServiceUrl + "/api/v1/product/" + productId;

        try {
            BaseResponseDto<ProductDto> response = webClientBuilder.build()
                    .get()
                    .uri(url)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                            Mono.error(new RuntimeException("Product ID " + productId + " not found"))
                    )
                    .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                            Mono.error(new RuntimeException("Product Service internal error"))
                    )
                    .bodyToMono(new ParameterizedTypeReference<BaseResponseDto<ProductDto>>() {})
                    .block();

            if (response == null || response.getData() == null) {
                return null;
            }
            return response.getData();

        } catch (WebClientRequestException e) {
            throw new RuntimeException("Product Service is unavailable (Connection Failed). Please try again later.");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public CustomerDto getCustomerById(Long customerId) {
        String url = customerServiceUrl + "/api/v1/customer/" + customerId;

        try {
            BaseResponseDto<CustomerDto> response = webClientBuilder.build()
                    .get()
                    .uri(url)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                            Mono.error(new RuntimeException("Customer ID " + customerId + " not found"))
                    )
                    .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                            Mono.error(new RuntimeException("Customer Service internal error"))
                    )
                    .bodyToMono(new ParameterizedTypeReference<BaseResponseDto<CustomerDto>>() {})
                    .block();

            if (response == null || response.getData() == null) {
                return null;
            }
            return response.getData();

        } catch (WebClientRequestException e) {
            throw new RuntimeException("Customer Service is unavailable (Connection Failed). Please try again later.");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
