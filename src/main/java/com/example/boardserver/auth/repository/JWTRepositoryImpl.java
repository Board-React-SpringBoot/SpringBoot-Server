package com.example.boardserver.auth.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class JWTRepositoryImpl implements JWTRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String KEY_PREFIX = "user:";

    @Override
    public void save(Long userId, String device, String refreshToken, Duration ttl) {
        String key = KEY_PREFIX + userId;
        redisTemplate.opsForHash().put(key, device, refreshToken);
        redisTemplate.expire(key, ttl);
    }

    @Override
    public boolean exists(Long userId, String device) {
        return redisTemplate.opsForHash().hasKey(KEY_PREFIX + userId, device);
    }

    @Override
    public void deleteRefresh(Long userId, String device) {
        redisTemplate.opsForHash().delete(KEY_PREFIX + userId, device);
    }

    @Override
    public void deleteAllRefresh(Long userId) {
        redisTemplate.delete(KEY_PREFIX + userId);
    }
}
