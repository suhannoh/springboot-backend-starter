package com.yonsai.starter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonsai.starter.common.response.ApiResponse;
import com.yonsai.starter.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

/**
 * Spring Security 설정
 *
 * Spring Security FilterChain : 모든 요청을 먼저 검사
 *  - UsernamePasswordAuthenticationFilter : /login 요청을 처리
 *  - CustomUserDetailsService : DB에서 User 검색
 * PasswordEncoder : 입력 비번 vs DB에 저장된 해시 비번 비교
 * Session : 로그인 성공 시 서버가 “출입증 정보”를 저장해둠
 * JSESSIONID : 브라우저가 들고 다니는 “출입증 번호 쿠키”
 *
 * successHandler , failureHandler : 상태코드 + ApiResponse JSON
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final ObjectMapper objectMapper;

  @Bean
  public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http
            // CORS 설정
            .cors(cors -> {})
            // CSRF 설정
            .csrf(AbstractHttpConfigurer::disable)
            // 로그인 설정
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(basic -> basic.disable())
            // 접근 권한 설정
            .authorizeHttpRequests(auth ->
              auth
                  // 접근 허용 설정
                      // preflight 허용 (React 연동 필수)
                      .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                      // 인증 없이 허용할 엔드포인트
                      .requestMatchers("/", "/error", "/login", "/signup").permitAll()
                      // 정적 리소스
                      .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                      // 나머지 url 들은 모두 로그인 필요
                      .requestMatchers("/admin-test").hasRole("ADMIN")
                      .requestMatchers("/user-test").hasRole("USER")
                      .anyRequest().authenticated()
            )
             // 동시 로그인 제한 설정
            .sessionManagement( session ->
                    // 최대 세션 수 1개로 제한
                    // 로그인 허용 / 기존 세션 만료
               session
                       .maximumSessions(1).expiredUrl("/login?expired=true")
            )
            // 로그아웃 설정
            .logout(logout ->
                    // 로그아웃 url 설정
               logout
                       .logoutUrl("/logout")
                       .logoutSuccessUrl("/")
                       .invalidateHttpSession(true)
                       .deleteCookies("JSESSIONID")
            );

    return http.build();
  }

  /**
   *  BCrypt 기반 PasswordEncoder Bean.
   *  * 비밀번호 암호화 및 인증 시 비밀번호 비교에 사용된다.
   * @return
   */
  @Bean
  public PasswordEncoder passwordEncoder () {
    return new BCryptPasswordEncoder();
  }

  /**
   * 인증 요청을 처리하는 AuthenticationManager Bean.
   * 내부적으로 UserDetailsService와 PasswordEncoder를 사용하여 인증을 수행한다.
   * @param config
   * @return
   * @throws Exception
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public SecurityContextRepository securityContextRepository() {
    return new HttpSessionSecurityContextRepository();
  }
}
