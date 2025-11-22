package com.example.boardserver.user.service;

import com.example.boardserver.common.code.status.ErrorStatus;
import com.example.boardserver.exception.handler.UserHandler;
import com.example.boardserver.user.converter.UserResponseConverter;
import com.example.boardserver.user.domain.User;
import com.example.boardserver.user.dto.UserResponse;
import com.example.boardserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    @Override
    public UserResponse.UserMyPageDTO getDetailUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        return UserResponseConverter.toUserMyPageDTO(user);
    }
}
