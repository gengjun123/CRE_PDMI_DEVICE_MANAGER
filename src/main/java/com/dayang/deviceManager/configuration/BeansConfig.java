package com.dayang.deviceManager.configuration;

import com.dayang.deviceManager.interceptor.LogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    LogInterceptor logInterceptor() {
        return new LogInterceptor();
    }
}