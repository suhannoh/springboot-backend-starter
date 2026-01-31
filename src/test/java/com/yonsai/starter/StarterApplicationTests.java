package com.yonsai.starter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StarterApplicationTests {

    @Autowired
    MockMvc mockMvc;

    /**
     * MockMvc로 서버 기동 + 기본 응답 구조 확인하는 테스트
     */
    @Test
    void start_all_test() throws Exception {
        mockMvc.perform(get("/")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value("Hello Final Project ,,,"))
                .andExpect(jsonPath("$.message").value("Success"));
    }

    /**
     * 전역 예외 처리(GlobalExceptionHandler) 동작 검증 테스트
     *
     * BusinessException이 컨트롤러에서 발생했을 때
     * 전역 예외 처리기가 이를 정상적으로 가로채어
     * 일관된 ApiResponse + ErrorResponse 형태로 반환하는지 검증한다.
     *
     * 검증 항목:
     * - HTTP 상태 코드가 400(BAD_REQUEST)인지
     * - success 값이 false인지
     * - ErrorCode가 BAD_REQUEST인지
     * - 에러 메시지가 정상적으로 전달되는지
     * - 에러 발생 시간이 포함되는지
     */
    @Test
    void global_error_catch_test() throws Exception {
        mockMvc.perform(get("/error-test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.error.msg").value("요청이 올바르지 않습니다."))
                .andExpect(jsonPath("$.error.timeStamp").exists());
    }
}
