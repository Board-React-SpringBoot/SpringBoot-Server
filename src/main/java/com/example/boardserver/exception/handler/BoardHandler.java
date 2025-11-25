package com.example.boardserver.exception.handler;

import com.example.boardserver.common.code.BaseErrorCode;
import com.example.boardserver.exception.GeneralException;

public class BoardHandler extends GeneralException {

    public BoardHandler(BaseErrorCode code) {
        super(code);
    }
}
