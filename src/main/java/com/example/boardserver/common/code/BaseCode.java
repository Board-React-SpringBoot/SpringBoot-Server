package com.example.boardserver.common.code;

import com.example.boardserver.common.code.dto.ReasonDTO;

public interface BaseCode {

    ReasonDTO getReason();

    ReasonDTO getReasonHttpStatus();
}
