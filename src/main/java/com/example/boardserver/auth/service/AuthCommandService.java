package com.example.boardserver.auth.service;

import com.example.boardserver.auth.dto.JoinRequestDTO;
import com.example.boardserver.auth.dto.JoinResponseDTO;

public interface AuthCommandService {

    /**
     * 회원가입 시 새로운 유저를 DB에 저장하는 Service
     * @param joinRequestDTO JoinRequestDTO
     * @return JoinResponseDTO
     */
    JoinResponseDTO saveUser(JoinRequestDTO joinRequestDTO);
}
