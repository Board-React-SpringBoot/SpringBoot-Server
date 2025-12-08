package com.example.boardserver.auth.dto.Oauth;

import lombok.ToString;

import java.util.Map;

@ToString
public class KakaoResponse implements OAuth2Response {

    private final Map<String, Object> attributes;
    private final Map<String, Object> properties;
    private final Map<String, Object> account;

    public KakaoResponse(Map<String, Object> attribute) {

        this.attributes = attribute;
        this.properties = (Map<String, Object>) attribute.get("properties");
        this.account = (Map<String, Object>) attribute.get("kakao_account");
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getEmail() {
        return account.get("email") != null ? account.get("email").toString() : null;
    }

    @Override
    public String getNickname() {
        return properties.get("nickname").toString();
    }

    @Override
    public String getProfile() {
        return properties.get("profile_image").toString();
    }
}
