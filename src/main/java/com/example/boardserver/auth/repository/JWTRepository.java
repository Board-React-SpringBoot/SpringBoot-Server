package com.example.boardserver.auth.repository;

import java.time.Duration;

public interface JWTRepository {

    /**
     * UserId와 Device를 기준으로 Refresh 토큰을 Redis에 저장하는 Repository 메소드
     * @param userId Long
     * @param device String
     * @param refreshToken String
     * @param ttl Duration
     */
    void save(Long userId, String device, String refreshToken, Duration ttl);

    /**
     * UserId와 Device를 기준으로 Refresh 토큰이 존재하는지 검증하는 Repository 메소드
     * @param userId Long
     * @param device String
     * @return boolean
     */
    boolean exists(Long userId, String device);

    /**
     * UserId와 Device를 기준으로 Refresh 토큰을 삭제하는 Repository 메소드
     * @param userId Long
     * @param device String
     */
    void deleteRefresh(Long userId, String device);

    /**
     * 특정 userId의 모든 Refresh 토큰을 삭제하는 Repository 메소드
     * @param userId Long
     */
    void deleteAllRefresh(Long userId);
}
