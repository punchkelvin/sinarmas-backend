package com.sinarmas.insurance.payment_service.client;

import com.sinarmas.insurance.common_lib.dto.response.BaseResponseDto;
import com.sinarmas.insurance.payment_service.dto.external.PolicyDto;
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
public class PolicyClient {
    private final WebClient.Builder webClientBuilder;

    @Value("${service.policy.url}")
    private String policyServiceUrl;

    public PolicyDto getPolicyById(Long policyId) {
        String url = policyServiceUrl + "/api/v1/policy/" + policyId;

        try {

            BaseResponseDto<PolicyDto> response = webClientBuilder.build()
                    .get().uri(url)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                            Mono.error(new RuntimeException("Policy ID " + policyId + " not found"))
                    )
                    .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                            Mono.error(new RuntimeException("Policy Service internal error"))
                    )
                    .bodyToMono(new ParameterizedTypeReference<BaseResponseDto<PolicyDto>>() {})
                    .block();

            if (response == null || response.getData() == null) {
                return null;
            }

            return response.getData();

        } catch (WebClientRequestException e) {
            throw new RuntimeException("Policy Service is unavailable (Connection Failed). Please try again later.");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }



    public void activatePolicy(Long policyId) {
        String url = policyServiceUrl + "/api/v1/policy/activate/" + policyId;

        try {
            webClientBuilder.build()
                    .put().uri(url)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                            Mono.error(new RuntimeException("Policy ID " + policyId + " not found"))
                    )
                    .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                            Mono.error(new RuntimeException("Policy Service internal error"))
                    )
                    .toBodilessEntity()
                    .block();

        } catch (WebClientRequestException e) {
            throw new RuntimeException("Policy Service is unavailable (Connection Failed). Please try again later.");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

}
