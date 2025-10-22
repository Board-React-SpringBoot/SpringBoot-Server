package com.example.boardserver.common.code.dto;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder  // 에러 응답을 전달할 DTO
public record ErrorReasonDTO(
        HttpStatus httpStatus,
        boolean isSuccess,
        String code,
        String message
) {
    public boolean getIsSuccess() {
        return isSuccess;
    }
}
