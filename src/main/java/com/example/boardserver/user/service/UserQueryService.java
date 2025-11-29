package com.example.boardserver.user.service;

import com.example.boardserver.user.domain.User;

public interface UserQueryService {

    User getDetailUser(Long userId);
}
