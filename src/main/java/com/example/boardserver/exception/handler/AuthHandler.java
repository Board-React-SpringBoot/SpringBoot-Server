package com.example.boardserver.exception.handler;

import com.example.boardserver.common.code.BaseErrorCode;
import com.example.boardserver.exception.GeneralException;

public class AuthHandler extends GeneralException {

    public AuthHandler(BaseErrorCode code) {
        super(code);
    }
}
