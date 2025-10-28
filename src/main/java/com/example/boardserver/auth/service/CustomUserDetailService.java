package com.example.boardserver.auth.service;

import com.example.boardserver.auth.dto.CustomUserDetails;
import com.example.boardserver.common.code.status.ErrorStatus;
import com.example.boardserver.user.domain.User;
import com.example.boardserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        ErrorStatus.MEMBER_NOT_FOUND.getMessage()
                ));

        return CustomUserDetails.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .nickname(user.getNickname())
                .build();
    }
}
