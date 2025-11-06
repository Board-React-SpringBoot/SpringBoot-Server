package com.example.boardserver.auth.dto.Oauth;

public interface OAuth2Response {

    /**
     * 소셜 로그인 API Registry 이름 반환
     * @return String
     */
    String getProvider();

    String getProviderId();

    /**
     * 소셜 로그인 계정의 Email
     * @return String
     */
    String getEmail();

    /**
     * 소셜 로그인 게정의 이름 또는 Nickname
     * @return String
     */
    String getNickname();

    /**
     * 소셜 로그인 계정의 프로필 사진
     * @return String
     */
    String getProfile();
}
