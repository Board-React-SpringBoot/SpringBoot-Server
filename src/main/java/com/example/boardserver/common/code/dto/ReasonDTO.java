package com.example.boardserver.common.code.dto;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ReasonDTO(
        HttpStatus httpStatus,
        boolean isSuccess,
        String code,
        String message
) {
    public boolean getIsSuccess() {
        return isSuccess;
    }
}
