package com.sinarmas.insurance.common_lib.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseRequestDto {
    private String statusCode;
    private LocalDateTime createdDate;
}
