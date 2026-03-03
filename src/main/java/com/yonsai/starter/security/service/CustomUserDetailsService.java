package com.yonsai.starter.security.service;

import com.yonsai.starter.security.entity.User;
import com.yonsai.starter.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *  CustomUserDetailsService
 *
 *  로그인 시 DB에서 사용자 정보를 조회하는 역할을 하는 클래스
 *
 *  Spring Security의 인증 과정에서 사용자 정보를 데이터베이스에서 조회하여
 *  UserDetails 타입으로 반환하는 서비스 구현체이다.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  // Spring Security가 /login 처리할 때 자동으로 호출
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    System.out.println("🔥 username 들어온 값: [" + username + "]");

    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

    return new CustomUserDetails(user);
  }
}