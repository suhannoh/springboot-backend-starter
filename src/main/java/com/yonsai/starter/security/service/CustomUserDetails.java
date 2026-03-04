package com.yonsai.starter.security.service;

import com.yonsai.starter.security.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * CustomUserDetails
 *
 * DB의 User 엔티티를 Spring Security가 사용하는 UserDetails 형태로 변환하는 어댑터 클래스.
 * 인증 과정에서 사용자 정보(아이디, 비밀번호, 권한, 계정 상태 등)를 제공한다.
 */

public class CustomUserDetails implements UserDetails {
  private final User user;

  public CustomUserDetails(User user) {
    this.user = user;
  }

  public Long getId() {
    return user.getId();
  }

  public User getUser() {
    return user;
  }


  @Override
  //권한 체크에 사용
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  // 아래는 MVP에선 true 고정으로 두고, 필요하면 잠금/만료 로직 확장
  @Override public boolean isAccountNonExpired() { return true; }
  @Override public boolean isAccountNonLocked() { return true; }
  @Override public boolean isCredentialsNonExpired() { return true; }
  @Override public boolean isEnabled() { return true; }

}
