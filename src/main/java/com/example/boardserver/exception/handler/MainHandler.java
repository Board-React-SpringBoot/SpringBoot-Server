package com.example.boardserver.exception.handler;

import com.example.boardserver.common.code.BaseErrorCode;
import com.example.boardserver.exception.GeneralException;

public class MainHandler extends GeneralException {

    public MainHandler(BaseErrorCode code) {
        super(code);
    }
}
