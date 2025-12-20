package com.example.boardserver.auth.controller.enums;

public enum SocialEnum {
    KAKAO, NAVER, GITHUB, GOOGLE;

    public String toLowerCase() {
        return name().toLowerCase();
    }
}
