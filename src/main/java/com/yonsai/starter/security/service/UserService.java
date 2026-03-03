package com.yonsai.starter.security.service;

import com.yonsai.starter.security.entity.User;
import com.yonsai.starter.exception.BusinessException;
import com.yonsai.starter.exception.ErrorCode;
import com.yonsai.starter.security.dto.SignupRequest;
import com.yonsai.starter.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  /**
   * 회원 가입 로직
   * @param userRequest
   *   - username
   *   - password
   * @return
   *   - userId , Message
   */
  @Transactional
  public Long signup (SignupRequest userRequest) {
    // 중복 검사
    if (userRepository.existsByUsername(userRequest.username())) {
      throw new BusinessException(ErrorCode.DUPLICATE_USERNAME);
    }
    // 비밀번호 암호화
    String encoded = passwordEncoder.encode(userRequest.password());
    // User 객체 생성
    User user = new User(userRequest.username(), encoded, User.Role.USER);
    // DB 저장 후 id 반환
    return userRepository.save(user).getId();
  }

}
