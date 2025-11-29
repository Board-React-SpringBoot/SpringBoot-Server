package com.example.boardserver.user.service;

import com.example.boardserver.user.dto.UserMyPageResponseDTO;
import com.example.boardserver.user.dto.UserRequestDTO;

public interface UserCommandService {

    /**
     * 닉네임을 변경하는 Service 메서드
     * @param userId Long
     * @param request UserRequestDTO.UpdateNickname
     * @return UserMyPageResponseDTO
     */
    UserMyPageResponseDTO updateNickname(Long userId, UserRequestDTO.UpdateNickname request);

    /**
     * 닉네임을 변경하는 Service 메서드
     * @param userId Long
     * @param request UserRequestDTO.UpdateProfile
     * @return UserMyPageResponseDTO
     */
    UserMyPageResponseDTO updateProfile(Long userId, UserRequestDTO.UpdateProfile request);
}
