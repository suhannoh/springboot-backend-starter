package com.yonsai.starter.home.controller;

import com.yonsai.starter.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    /**
     * 홈 테스트 API
     *
     * ApiResponse 성공 응답 구조 예시를 보여주는 기본 엔드포인트.
     * 템플릿 프로젝트가 정상 동작하는지 확인하는 용도로 사용한다.
     *
     * @return ApiResponse 형식의 성공 메시지
     */
    @GetMapping("/")
    public ResponseEntity<ApiResponse<String>> home () {

         return ResponseEntity.ok(ApiResponse.success("Hello Final Project ,,,"));
    }

    /**
     * Error 테스트 API
     * - IllegalArgumentException 예외를 던진다.
     */
    @GetMapping("/error-test")
    public void errorTest () {
        throw new IllegalArgumentException();
    }
}
