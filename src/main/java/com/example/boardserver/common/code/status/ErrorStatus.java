package com.example.boardserver.common.code.status;

import com.example.boardserver.common.code.BaseErrorCode;
import com.example.boardserver.common.code.dto.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 대표적인 에러 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러"),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // 유저 관련 에러
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER4001", "유저가 없습니다."),
    EMAIL_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "USER4002", "이미 존재하는 계정입니다."),
    NICKNAME_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "USER4003", "이미 존재하는 닉네임입니다."),

    // 작성 예시
    TEST_EXCEPTION(HttpStatus.BAD_REQUEST, "TEST4001", "테스트용 에러입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .isSuccess(false)
                .code(code)
                .message(message)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .isSuccess(false)
                .httpStatus(httpStatus)
                .code(code)
                .message(message)
                .build();
    }
}
