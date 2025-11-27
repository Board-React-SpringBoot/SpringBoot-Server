package com.example.boardserver.auth.converter;

import com.example.boardserver.auth.dto.JoinRequestDTO;
import com.example.boardserver.auth.dto.JoinResponseDTO;
import com.example.boardserver.user.domain.User;
import com.example.boardserver.user.domain.enums.RoleType;

import java.util.Optional;

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
     * OAuth2 로그인 정보를 User Entity 객체로 변환하는 메소드
     * @param email String
     * @param nickname String
     * @param password String
     * @param profile String
     * @return User
     */
    public static User toUserEntity(String email, String nickname,  String profile, String password) {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .profile(profile)
                .role(RoleType.USER)
                .social(true)
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
                .profile(
                        Optional.ofNullable(user.getProfile())
                                .filter(s -> !s.isEmpty())
                                .orElse(null)
                )
                .role(user.getRole())
                .social(user.getSocial())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
