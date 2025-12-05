package com.example.boardserver.auth.dto.Oauth;

import lombok.ToString;

import java.util.Map;

@ToString
public class GitHubResponse implements OAuth2Response{

    private final Map<String, Object> attribute;

    public GitHubResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "github";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        Object email = attribute.get("email");
        return email != null ? email.toString() : null;
    }

    @Override
    public String getNickname() {
        return attribute.get("name").toString();
    }

    @Override
    public String getProfile() {
        return attribute.get("avatar_url").toString();
    }
}
