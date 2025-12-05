package com.example.boardserver.exception.handler;

import com.example.boardserver.common.code.BaseErrorCode;
import com.example.boardserver.common.code.dto.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

@Getter
public class OAuth2Handler extends OAuth2AuthenticationException {

    private final BaseErrorCode code;

    public OAuth2Handler(BaseErrorCode code) {
        super(new OAuth2Error(
                code.getReason().code(),
                code.getReason().message(),
                null
        ));

        this.code = code;
    }

    public ErrorReasonDTO getErrorReason() {
        return this.code.getReason();
    }

    public ErrorReasonDTO getErrorReasonHttpStatus() {
        return this.code.getReasonHttpStatus();
    }
}
