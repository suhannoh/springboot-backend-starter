package com.yonsai.starter.security.controller;

import com.yonsai.starter.common.response.ApiResponse;
import com.yonsai.starter.security.dto.LoginRequest;
import com.yonsai.starter.security.dto.SignupRequest;
import com.yonsai.starter.security.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final SecurityContextRepository securityContextRepository;

  /**
   * 회원가입 API
   * @param req
   * @return
   */
  @PostMapping("/signup")
  public ApiResponse<Long> signup(@Valid @RequestBody SignupRequest req) {
    Long userId = userService.signup(req);
    return ApiResponse.success(userId, "Signup Success");
  }

  /**
   * 로그인 API
   *
   * Security Login을 직접 세팅
   * @param req
   * @return
   */

  @PostMapping("/login")
  public ApiResponse<String> login(@Valid @RequestBody LoginRequest req,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {

    // 아직 인증되지 않은 UsernamePasswordAuthenticationToken 생성
    // (username, password를 담은 인증 요청 객체)
    UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(req.username(), req.password());

    // 실제 로그인 검증 구간 AuthenticationManager를 통해 실제 인증 수행
    // 내부적으로 UserDetailsService + PasswordEncoder가 동작
    Authentication authentication = authenticationManager.authenticate(authenticationToken);
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authentication);
    // 로그인 정보 저장 인증이 완료된 Authentication 객체를 SecurityContext에 저장
    // 이후 요청부터는 로그인된 사용자로 인식됨 (세션 기반)
    securityContextRepository.saveContext(context, request, response);

    return ApiResponse.success(null, "Login Success");
  }
}
