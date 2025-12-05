package com.example.boardserver.auth.service;

import com.example.boardserver.auth.converter.JoinConverter;
import com.example.boardserver.auth.dto.CustomUserDetails;
import com.example.boardserver.auth.dto.Oauth.GitHubResponse;
import com.example.boardserver.auth.dto.Oauth.GoogleResponse;
import com.example.boardserver.auth.dto.Oauth.NaverResponse;
import com.example.boardserver.auth.dto.Oauth.OAuth2Response;
import com.example.boardserver.common.code.status.ErrorStatus;
import com.example.boardserver.exception.handler.OAuth2Handler;
import com.example.boardserver.user.domain.User;
import com.example.boardserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService extends DefaultOAuth2UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${oauth2.password}")
    String oAuthPassword;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        ErrorStatus.USER_NOT_FOUND.getMessage()
                ));

        return CustomUserDetails.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .nickname(user.getNickname())
                .build();
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(request);
        System.out.println("oAuth2User = " + oAuth2User);

        String registrationId = request.getClientRegistration().getRegistrationId();

        OAuth2Response oAuth2Response = null;

        switch (registrationId) {
            case "naver" -> oAuth2Response = new NaverResponse(oAuth2User.getAttributes());

            case "google" -> oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());

            case "github" -> oAuth2Response = new GitHubResponse(oAuth2User.getAttributes());

            default -> throw new OAuth2Handler(ErrorStatus.OAUTH_NOT_SUPPORTED);
        }

        if (oAuth2Response.getEmail() == null) throw new OAuth2Handler(ErrorStatus.SOCIAL_EMAIL_PRIVATE);

        System.out.println("Social Login User = " + oAuth2Response);

        Optional<User> user = userRepository.findByEmail(oAuth2Response.getEmail());
        User savedUser;

        if (user.isEmpty()) {
            String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String nickname = oAuth2Response.getNickname() + "#" + now.substring(10);
            String tempPassword = passwordEncoder.encode(oAuthPassword + "_" + now);

            User newUser = JoinConverter.toUserEntity(oAuth2Response.getEmail(), nickname, oAuth2Response.getProfile(), tempPassword);
            savedUser = userRepository.save(newUser);
        } else {
            savedUser = user.get();
        }
        
        System.out.println("소셜 로그인 유저 정보 획득 완료");

        return CustomUserDetails.builder()
                .userId(savedUser.getUserId())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .nickname(savedUser.getNickname())
                .build();
    }
}
