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

    // 인증 & 인가 관련 에러
    JWT_NOT_FOUND(HttpStatus.UNAUTHORIZED, "JWT4011", "JWT 토큰이 존재하지 않습니다."),
    JWT_EXPIRED(HttpStatus.UNAUTHORIZED, "JWT4012", "JWT 토큰이 만료되었습니다."),
    JWT_MALFORMED(HttpStatus.UNAUTHORIZED, "JWT4013", "잘못된 형식의 JWT 토큰입니다."),
    JWT_UNSUPPORTED(HttpStatus.UNAUTHORIZED, "JWT4014", "지원하지 않는 JWT 토큰입니다."),
    JWT_SIGNATURE_FAILED(HttpStatus.UNAUTHORIZED, "JWT4015", "JWT 토큰의 서명이 올바르지 않습니다"),
    REFRESH_NOT_FOUND(HttpStatus.UNAUTHORIZED, "JWT4016", "Refresh 토큰이 존재하지 않습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "JWT4017", "유효하지 않은 Refresh 토큰입니다."),

    // 유저 관련 에러
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER4001", "유저가 없습니다."),
    EMAIL_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "USER4002", "이미 존재하는 계정입니다."),
    NICKNAME_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "USER4003", "이미 존재하는 닉네임입니다."),

    // 게시물 관련 에러
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "BOARD4001", "게시물이 존재하지 않습니다."),
    BOARD_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "BOARD4002", "해당 게시물에 접근할 권한이 없습니다."),

    FILE_IS_EMPTY(HttpStatus.UNAUTHORIZED, "FILE4001", "업로드할 파일이 없습니다."),
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "FILE4002", "파일을 찾을 수 없습니다."),
    FILE_INCORRECT_URL(HttpStatus.NOT_FOUND, "FILE4003", "파일 경로가 잘못되었습니다."),
    FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FILE5001", "파일 저장 중 오류가 발생했습니다."),

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
