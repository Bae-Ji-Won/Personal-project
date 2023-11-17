package com.example.singleproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware(){             // auditing을 통해 자동으로 이름(username)이 들어감 -> 나중에 로그인 기능 구현시 해당 이름 넣으면 됨
        return () -> Optional.of("username");
    }
}
