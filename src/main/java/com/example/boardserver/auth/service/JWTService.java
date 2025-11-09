package com.example.boardserver.auth.service;

public interface JWTService {

    /**
     * Refresh 토큰을 받아 새로운 Access 토큰을 생성하는 서비스 메소드
     * @param refresh  String
     * @return String
     */
    String reissueAccessToken(String refresh);
}
