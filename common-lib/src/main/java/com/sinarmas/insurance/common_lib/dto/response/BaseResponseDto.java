package com.sinarmas.insurance.common_lib.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponseDto<T> {
    private String statusMessage;
    private String statusCode;
    private LocalDateTime createdDate;
    private String message;
    private T data;
}
