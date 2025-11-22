package com.example.boardserver.user.service;

import com.example.boardserver.user.dto.UserResponse;

public interface UserQueryService {

    UserResponse.UserMyPageDTO getDetailUser(Long userId);
}
