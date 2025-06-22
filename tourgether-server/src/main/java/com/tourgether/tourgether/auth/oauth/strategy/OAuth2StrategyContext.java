package com.tourgether.tourgether.auth.oauth.strategy;

import com.tourgether.tourgether.member.enums.Provider;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2StrategyContext {

    private final List<OAuth2Strategy> oAuth2StrategyList;
    private final Map<Provider, OAuth2Strategy> oAuth2StrategyMap = new HashMap<>();

    @PostConstruct
    public void init() {
        for (OAuth2Strategy strategy : oAuth2StrategyList) {
            oAuth2StrategyMap.put(strategy.getProvider(), strategy);
        }
    }

    public OAuth2Strategy getStrategy(String providerType) {
        Provider provider = Provider.from(providerType);
        OAuth2Strategy strategy = oAuth2StrategyMap.get(provider);
        if (strategy == null) {
            throw new IllegalArgumentException(String.format("%s는 지원하지 않는 소셜입니다.", provider));
        }
        return strategy;
    }
}
