package com.pbl4.monolingo.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {
    @Bean

    public CacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("otpCache");
        cacheManager.setCaffeine(caffeineConfig());
        return cacheManager;

    }

    public Caffeine<Object, Object> caffeineConfig() {

        return Caffeine.newBuilder()

                .expireAfterWrite(10, TimeUnit.MINUTES)

                .maximumSize(100); // bạn có thể đặt kích thước tối đa cho cache nếu muốn

    }
}
//    @Bean
//    public ConcurrentMapCacheManager cacheManager(){
//
//        return new ConcurrentMapCacheManager("otpCache");
//    }