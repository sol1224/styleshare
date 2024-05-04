package proj.petbuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

/**
 * Spring Security 기본 로그인 화면 제거
 **/
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)

/** @EnableJpaAuditing : 엔티티의 생성시각, 수정시각을 자동으로 기록 **/
@EnableJpaAuditing
public class PetbuddyApplication {
    public static void main(String[] args) {
        SpringApplication.run(PetbuddyApplication.class, args);
    }

    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer customize() {
        return p -> {
            p.setOneIndexedParameters(true);	// page default: 1부터 시작
//            p.setMaxPageSize(30);			// size=10
        };
    }
}