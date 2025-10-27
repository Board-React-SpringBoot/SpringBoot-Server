package com.example.boardserver.auth.service;

import com.example.boardserver.auth.converter.JoinConverter;
import com.example.boardserver.auth.dto.JoinRequestDTO;
import com.example.boardserver.auth.dto.JoinResponseDTO;
import com.example.boardserver.common.code.status.ErrorStatus;
import com.example.boardserver.exception.handler.AuthHandler;
import com.example.boardserver.user.domain.User;
import com.example.boardserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthCommandServiceImpl implements AuthCommandService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public JoinResponseDTO saveUser(JoinRequestDTO request) {

        if(userRepository.existsByEmail(request.email())) {
            throw new AuthHandler(ErrorStatus.EMAIL_ALREADY_EXIST);
        } else if (userRepository.existsByNickname(request.nickname())) {
            throw new AuthHandler(ErrorStatus.NICKNAME_ALREADY_EXIST);
        }

        User user = JoinConverter.toUserEntity(request);
        user.encodedPassword(passwordEncoder.encode(user.getPassword()));

        return JoinConverter.toJoinResponseDTO(userRepository.save(user));
    }
}
