package com.example.boardserver.auth.service;

public interface JWTService {

    /**
     * Refresh 토큰을 받아 새로운 Access 토큰을 생성하는 서비스 메소드
     * @param refresh  String
     * @return String
     */
    String reissueAccessToken(String refresh);

    /**
     * Refresh 토큰을 새로 발급하는 서비스 메소드
     * @param refresh String
     * @return String
     */
    String rotateRefreshToken(String refresh);

    /**
     * Redis에 Refresh 토큰, 유저 ID. 로그인한 기기 종류를 저장하는 서비스 Method
     * @param refresh String
     * @param device String
     */
    void saveRefreshToken(String refresh, String device);

    /**
     * Redis에 특정 유저가 해당 기기로 로그인을 했는지 검증하는 서비스 Method
     * @param refresh String
     * @param device String
     * @return Boolean
     */
    Boolean existsRefreshToken(String refresh, String device);

    /**
     * Redis에 저장된 특정 유저의 특정 기기에서 로그인한 Refresh 토큰 삭제하는 서비스 Method
     * @param refresh String
     * @param device String
     */
    void deleteRefreshToken(String refresh, String device);

    /**
     * 특정 유저가 로그인한 모든 기기의 Refresh 토큰을 삭제하는 서비스 Method
     * @param refresh String
     */
    void deleteAllRefreshTokens(String refresh);
}
