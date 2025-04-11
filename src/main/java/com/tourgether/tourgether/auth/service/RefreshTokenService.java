package com.tourgether.tourgether.auth.service;

import com.tourgether.tourgether.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RedisRepository redisRepository;

    private final String REFRESH_TOKEN_PREFIX = "refreshToken:";

    public void saveRefreshToken(Long memberId, String refreshToken, long expiredMs) {
        String key = REFRESH_TOKEN_PREFIX + memberId;
        redisRepository.save(key, refreshToken, expiredMs);
    }

    public boolean validateRefreshToken(Long memberId, String refreshToken) {
        String key = REFRESH_TOKEN_PREFIX + memberId;
        if (!redisRepository.exists(key)) {
            return false;
        }
        String storedRefreshToken = redisRepository.findByKey(key).get();
        return storedRefreshToken.equals(refreshToken);
    }
}
