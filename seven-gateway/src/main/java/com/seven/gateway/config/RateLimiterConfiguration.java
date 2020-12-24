package com.seven.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @deprecated 路由限流配置
 * @author v_chendongdong
 * @date 2020/12/21
 */
@Configuration
public class RateLimiterConfiguration {

    @Bean("remoteAddrKeyResolver")
    public KeyResolver remoteAddrKeyResolver(){
        return new KeyResolver() {
            public Mono<String> resolve(ServerWebExchange exchange) {
                return Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
            }
        };
    }
}
