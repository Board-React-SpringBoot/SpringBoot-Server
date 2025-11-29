package com.example.boardserver.user.service;

import com.example.boardserver.common.code.status.ErrorStatus;
import com.example.boardserver.exception.handler.UserHandler;
import com.example.boardserver.user.converter.UserConverter;
import com.example.boardserver.user.domain.User;
import com.example.boardserver.user.dto.UserMyPageResponseDTO;
import com.example.boardserver.user.dto.UserRequestDTO;
import com.example.boardserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;

    @Override
    public UserMyPageResponseDTO updateNickname(Long userId, UserRequestDTO.UpdateNickname request) {
        if (userRepository.existsByNickname(request.nickname())) throw new UserHandler(ErrorStatus.NICKNAME_ALREADY_EXIST);
        User user = userRepository.findById(userId).orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        user.changeNickname(request.nickname());

        return UserConverter.toUserMyPageResponseDTO(userRepository.save(user));
    }

    @Override
    public UserMyPageResponseDTO updateProfile(Long userId, UserRequestDTO.UpdateProfile request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        user.changeProfile(request.profile());

        return UserConverter.toUserMyPageResponseDTO(userRepository.save(user));
    }
}
