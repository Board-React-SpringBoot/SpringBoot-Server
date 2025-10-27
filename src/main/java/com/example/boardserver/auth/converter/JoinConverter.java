package com.example.boardserver.auth.converter;

import com.example.boardserver.auth.dto.JoinRequestDTO;
import com.example.boardserver.auth.dto.JoinResponseDTO;
import com.example.boardserver.user.domain.User;

public class JoinConverter {

    /**
     * JoinRequestDTO를 User Entity 객체로 변환하는 메소드
     * @param request JoinRequestDTO
     * @return User
     */
    public static User toUserEntity(JoinRequestDTO request) {
        return User.builder()
                .email(request.email())
                .nickname(request.nickname())
                .password(request.password())
                .profile(request.profile())
                .build();
    }

    /**
     * User Entity를 JoinResponseDTO 객체로 변환하는 메소드
     * @param user User
     * @return JoinResponseDTO
     */
    public static JoinResponseDTO toJoinResponseDTO(User user) {
        return JoinResponseDTO.builder()
                .userid(user.getUserId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profile(user.getProfile())
                .role(user.getRole())
                .social(user.getSocial())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
