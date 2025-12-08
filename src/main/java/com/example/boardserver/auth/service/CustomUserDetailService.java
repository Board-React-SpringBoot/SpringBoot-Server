package com.example.boardserver.auth.service;

import com.example.boardserver.auth.converter.JoinConverter;
import com.example.boardserver.auth.dto.CustomUserDetails;
import com.example.boardserver.auth.dto.Oauth.*;
import com.example.boardserver.common.code.status.ErrorStatus;
import com.example.boardserver.exception.handler.OAuth2Handler;
import com.example.boardserver.user.domain.User;
import com.example.boardserver.user.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        // System.out.println("oAuth2User = " + oAuth2User);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("oAuth2User = " + gson.toJson(oAuth2User.getAttributes()));

        String registrationId = request.getClientRegistration().getRegistrationId();

        OAuth2Response oAuth2Response = null;

        switch (registrationId) {
            case "naver" -> oAuth2Response = new NaverResponse(oAuth2User.getAttributes());

            case "google" -> oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());

            // case "github" -> oAuth2Response = new GitHubResponse(oAuth2User.getAttributes());
            case "github" -> oAuth2Response = handleGitHubEmail(request, oAuth2User);

            case "kakao" -> oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());

            default -> throw new OAuth2Handler(ErrorStatus.OAUTH_NOT_SUPPORTED);
        }

        if (oAuth2Response.getEmail() == null) throw new OAuth2Handler(ErrorStatus.SOCIAL_EMAIL_PRIVATE);

        System.out.println("Social Login User = " + gson.toJson(oAuth2Response));

        OAuth2Response finalOAuth2Response = oAuth2Response;
        User savedUser = userRepository.findByEmail(oAuth2Response.getEmail())
                .orElseGet(() -> createSocialUser(finalOAuth2Response));
        
        System.out.println("소셜 로그인 유저 정보 획득 완료");

        return CustomUserDetails.builder()
                .userId(savedUser.getUserId())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .nickname(savedUser.getNickname())
                .build();
    }

    /**
     * --- Github private email 가져오기 ---
     */
    private OAuth2Response handleGitHubEmail(OAuth2UserRequest request, OAuth2User oAuth2User) {

        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());

        // 기본 email
        Object email = attributes.get("email");

        if (email == null) {
            String accessToken = request.getAccessToken().getTokenValue();

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            headers.set("Accept", "application/vnd.github+json");

            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<List<Map<String, Object>>> emailResponse =
                    restTemplate.exchange(
                            "https://api.github.com/user/emails",
                            HttpMethod.GET,
                            entity,
                            new ParameterizedTypeReference<>() {}
                    );

            List<Map<String, Object>> emails = emailResponse.getBody();

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            System.out.println("GitHub Email = " + gson.toJson(emails));

            if (emails != null) {
                for (Map<String, Object> e : emails) {
                    Boolean primary = (Boolean) e.get("primary");
                    Boolean verified = (Boolean) e.get("verified");
                    if (Boolean.TRUE.equals(primary) && Boolean.TRUE.equals(verified)) {
                        attributes.put("email", e.get("email"));
                        break;
                    }
                }
            }
        }

        return new GitHubResponse(attributes);
    }

    /**
     * --- 소셜 로그인 신규 유저 생성 ---
     */
    private User createSocialUser(OAuth2Response oAuth2Response) {

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String nickname = oAuth2Response.getNickname() + "#" + now.substring(10);
        String tempPassword = passwordEncoder.encode(oAuthPassword + "_" + now);

        User newUser = JoinConverter.toUserEntity(
                oAuth2Response.getEmail(),
                nickname,
                oAuth2Response.getProfile(),
                tempPassword
        );

        return userRepository.save(newUser);
    }
}
