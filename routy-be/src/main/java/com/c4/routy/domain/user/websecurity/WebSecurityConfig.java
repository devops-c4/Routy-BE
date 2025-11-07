package com.c4.routy.domain.user.websecurity;

import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

// 시큐리티 설정 클래스
// 암호화 빈등록
// 필터 체인 등록
// AuthenticationManager 등록
@Configuration
public class WebSecurityConfig {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    public WebSecurityConfig(JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(jwtAuthenticationProvider));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtUtil jwtUtil) throws Exception {
        http
                // csrf 토큰 사용 X
                .csrf(csrf -> csrf.disable())

                // 해당 경로 요청 허용
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("*").permitAll()
                        .requestMatchers("/user/register", "/validation/sendmail").permitAll()
                        .requestMatchers("/file/**").permitAll()
                        .anyRequest().authenticated())

                // 세션 방식 사용 X
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 필터 추가
                .addFilter(getAuthenticationManager(authenticationManager(), jwtUtil))

                // 로그인 후 요청에 대한 필터 (JWT 토큰 인증)
                .addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }

    private Filter getAuthenticationManager(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        return new AuthenticationFilter(authenticationManager, jwtUtil);
    }
}
