package com.example.singleproject.config;

import com.example.singleproject.domain.UserAccount;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

/* Spring Data REST 추가 설정 - userId 노출
    - data rest 기본 설정은 id를 감추는 것인데, 회원 계정에 한해서 `userId`가 노출되게끔 해줘야 함
 */
@Configuration
public class DataRestConfig {

    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {
        return RepositoryRestConfigurer.withConfig((config, cors) ->
                config.exposeIdsFor(UserAccount.class)
        );
    }

}