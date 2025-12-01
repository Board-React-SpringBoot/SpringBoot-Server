package com.example.boardserver.exception.handler;

import com.example.boardserver.common.code.BaseErrorCode;
import com.example.boardserver.exception.GeneralException;

public class FileHandler extends GeneralException {

    public FileHandler(BaseErrorCode code) {
        super(code);
    }
}
