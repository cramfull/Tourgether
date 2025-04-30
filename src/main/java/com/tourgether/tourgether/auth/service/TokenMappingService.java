package com.tourgether.tourgether.auth.service;

import java.util.Optional;

import com.tourgether.tourgether.auth.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenMappingService {

    private final RedisRepository redisRepository;

    private final String TOKEN_MAPPING_PREFIX = "tokenMapping:";

    public void saveTokenCodeMapping(Long memberId, String tokenCode, long expiredTime){
        String key = TOKEN_MAPPING_PREFIX + tokenCode;
        redisRepository.save(key, String.valueOf(memberId), expiredTime);
    }

    public Optional<Long> getMemberIdByTokenCode(String tokenCode) {
        String key = TOKEN_MAPPING_PREFIX + tokenCode;
        String memberID = redisRepository.findByKey(key).orElseThrow(() -> new UserNotFoundException("redis expired or not found mapping"));
        return Optional.of(Long.valueOf(memberID));
    }

    public void deleteMappingByTokenCode(String tokenCode) {
        String key = TOKEN_MAPPING_PREFIX + tokenCode;
        redisRepository.deleteByKey(key);
    }
}
