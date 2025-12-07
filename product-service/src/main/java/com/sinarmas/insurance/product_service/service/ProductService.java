package com.sinarmas.insurance.product_service.service;

import com.sinarmas.insurance.common_lib.constant.CommonConstants;
import com.sinarmas.insurance.common_lib.dto.response.BaseResponseDto;
import com.sinarmas.insurance.product_service.dto.common.ProductDto;
import com.sinarmas.insurance.product_service.dto.request.AddProductRequestDto;
import com.sinarmas.insurance.product_service.dto.request.UpdateProductRequestDto;
import com.sinarmas.insurance.product_service.entity.InsuranceProduct;
import com.sinarmas.insurance.product_service.entity.InsuranceType;
import com.sinarmas.insurance.product_service.repository.InsuranceProductRepository;
import com.sinarmas.insurance.product_service.repository.InsuranceTypeRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final InsuranceProductRepository insuranceProductRepository;
    private final InsuranceTypeRepository insuranceTypeRepository;
    private final List<PriceCalculatorStrategy> strategies;
    private final ConcurrentHashMap<String, PriceCalculatorStrategy> strategyMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void initStrategyMap() {
        for(PriceCalculatorStrategy strategy : strategies) {
            strategyMap.put(strategy.getSupportedType(), strategy);
        }
    }

    public BaseResponseDto getAllProducts(){
        BaseResponseDto productResponseDto = BaseResponseDto
                .builder()
                .statusCode(CommonConstants.SUCCESS_CODE)
                .statusMessage(CommonConstants.SUCCESS_MESSAGE)
                .message(CommonConstants.SUCCESS_MESSAGE)
                .createdDate(LocalDateTime.now())
                .build();

        try {
            List<InsuranceProduct> products = insuranceProductRepository.findAll();

            List<ProductDto> productDtos = new ArrayList<>();

            for(InsuranceProduct insuranceProduct : products) {
                productDtos.add(mapToProductDto(insuranceProduct, calculateFinalPremium(insuranceProduct)));
            }

            productResponseDto.setData(productDtos);

        } catch (Exception e) {
            productResponseDto.setMessage(e.getMessage());
            productResponseDto.setStatusMessage(CommonConstants.FAIL_MESSAGE);
            productResponseDto.setStatusCode(CommonConstants.FAIL_CODE);
        }

        return productResponseDto;

    }

    public BaseResponseDto getProduct(Long id){
        BaseResponseDto productResponseDto = BaseResponseDto
                .builder()
                .statusCode(CommonConstants.SUCCESS_CODE)
                .statusMessage(CommonConstants.SUCCESS_MESSAGE)
                .message(CommonConstants.SUCCESS_MESSAGE)
                .createdDate(LocalDateTime.now())
                .build();

        try {
            InsuranceProduct product = insuranceProductRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Product Not Found"));

            productResponseDto.setData(mapToProductDto(product, calculateFinalPremium(product)));

        } catch (Exception e) {
            productResponseDto.setMessage(e.getMessage());
            productResponseDto.setStatusMessage(CommonConstants.FAIL_MESSAGE);
            productResponseDto.setStatusCode(CommonConstants.FAIL_CODE);
        }

        return productResponseDto;

    }

    public BaseResponseDto addProduct(AddProductRequestDto addProductRequestDto){
        BaseResponseDto productResponseDto = BaseResponseDto
                .builder()
                .statusCode(CommonConstants.SUCCESS_CODE)
                .statusMessage(CommonConstants.SUCCESS_MESSAGE)
                .message(CommonConstants.SUCCESS_MESSAGE)
                .createdDate(LocalDateTime.now())
                .build();

        try {

            if(insuranceProductRepository.existsByName(addProductRequestDto.getName())){
                throw new EntityExistsException("Product with the name: " + addProductRequestDto.getName() + " already exists");
            }

            InsuranceType insuranceType = insuranceTypeRepository.findById(addProductRequestDto.getInsuranceType())
                    .orElseThrow(() -> new EntityNotFoundException("Insurance Type doesn't exist"));

            InsuranceProduct insuranceProduct = InsuranceProduct.builder()
                    .name(addProductRequestDto.getName())
                    .basePremium(addProductRequestDto.getBasePremium())
                    .coverageAmount(addProductRequestDto.getCoverageAmount())
                    .currency(addProductRequestDto.getCurrency())
                    .insuranceType(insuranceType)
                    .build();

            insuranceProductRepository.save(insuranceProduct);

            productResponseDto.setData(insuranceProduct);


        } catch (Exception e) {
            productResponseDto.setMessage(e.getMessage());
            productResponseDto.setStatusMessage(CommonConstants.FAIL_MESSAGE);
            productResponseDto.setStatusCode(CommonConstants.FAIL_CODE);
        }

        return productResponseDto;

    }

    public BaseResponseDto updateProduct(Long id, UpdateProductRequestDto updateProductRequestDto){
        BaseResponseDto productResponseDto = BaseResponseDto
                .builder()
                .statusCode(CommonConstants.SUCCESS_CODE)
                .statusMessage(CommonConstants.SUCCESS_MESSAGE)
                .message(CommonConstants.SUCCESS_MESSAGE)
                .createdDate(LocalDateTime.now())
                .build();

        try {
            InsuranceProduct productToUpdate = insuranceProductRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Product Not Found"));

            InsuranceType insuranceType = insuranceTypeRepository.findById(updateProductRequestDto.getInsuranceType())
                    .orElseThrow(() -> new EntityNotFoundException("Insurance Type Not Found"));


            productToUpdate.setName(updateProductRequestDto.getName());
            productToUpdate.setCurrency(updateProductRequestDto.getCurrency());
            productToUpdate.setBasePremium(updateProductRequestDto.getBasePremium());
            productToUpdate.setCoverageAmount(updateProductRequestDto.getCoverageAmount());
            productToUpdate.setInsuranceType(insuranceType);

            insuranceProductRepository.save(productToUpdate);

            productResponseDto.setData(mapToProductDto(productToUpdate, calculateFinalPremium(productToUpdate)));

        } catch (Exception e) {
            productResponseDto.setMessage(e.getMessage());
            productResponseDto.setStatusMessage(CommonConstants.FAIL_MESSAGE);
            productResponseDto.setStatusCode(CommonConstants.FAIL_CODE);
        }

        return productResponseDto;

    }



    public BaseResponseDto deleteProduct(Long id){
        BaseResponseDto productResponseDto = BaseResponseDto
                .builder()
                .statusCode(CommonConstants.SUCCESS_CODE)
                .statusMessage(CommonConstants.SUCCESS_MESSAGE)
                .message(CommonConstants.SUCCESS_MESSAGE)
                .createdDate(LocalDateTime.now())
                .build();

        try {
            InsuranceProduct productToDelete = insuranceProductRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Product Not Found"));

            insuranceProductRepository.delete(productToDelete);

        } catch (Exception e) {
            productResponseDto.setMessage(e.getMessage());
            productResponseDto.setStatusMessage(CommonConstants.FAIL_MESSAGE);
            productResponseDto.setStatusCode(CommonConstants.FAIL_CODE);
        }

        return productResponseDto;

    }



    private BigDecimal calculateFinalPremium(InsuranceProduct insuranceProduct) {
        PriceCalculatorStrategy priceCalculatorStrategy = strategyMap.get(insuranceProduct.getInsuranceType().getName());

        BigDecimal finalPrice;

        if(priceCalculatorStrategy != null) {
            finalPrice = priceCalculatorStrategy.calculateFinalPrice(insuranceProduct.getBasePremium());
        } else {
            finalPrice = insuranceProduct.getBasePremium();
        }

        return finalPrice;
    }

    private ProductDto mapToProductDto(InsuranceProduct insuranceProduct, BigDecimal finalPrice) {
        return ProductDto.builder()
                .id(insuranceProduct.getId())
                .finalPremium(finalPrice)
                .maxCoverage(insuranceProduct.getCoverageAmount())
                .name(insuranceProduct.getName())
                .typeName(insuranceProduct.getInsuranceType().getName())
                .currency(insuranceProduct.getCurrency())
                .build();
    }


}
