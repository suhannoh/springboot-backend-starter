package com.yonsai.starter.common.response;

import com.yonsai.starter.exception.ErrorCode;
import com.yonsai.starter.exception.ErrorResponse;

import java.time.LocalDateTime;

/**
 * API 성공 응답을 감싸는 공통 Response 래퍼
 *
 * 모든 컨트롤러의 성공 응답을 동일한 구조로 반환하기 위해 사용한다.
 * 프론트엔드와의 응답 형식을 통일하고, 유지보수를 쉽게 한다.
 * 구조:
 * @param success 요청 성공 여부
 * @param data 실제 응답 데이터
 * @param message 사용자에게 전달할 메시지
 * @param <T>
 */
public record ApiResponse<T>(
        boolean success,
        T data,
        String message,
        ErrorResponse error
) {

    /**
     * 기본 성공 메시지를 사용하는 응답 생성
     *
     * @param data 응답 데이터
     * @return 성공 응답 객체
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, "Success" ,null);
    }

    /**
     * 사용자 정의 메시지를 포함한 성공 응답 생성
     *
     * @param data 응답 데이터
     * @param message 사용자 메시지
     * @return 성공 응답 객체
     */
    public static <T> ApiResponse<T> success(T data , String message) {
        return new ApiResponse<>(true, data, message ,null);
    }

    /**
     * 실패 응답을 생성하는 공통 팩토리 메서드
     *
     * 전역 예외 처리기(GlobalExceptionHandler)에서 호출되며,
     * 데이터가 없는 실패 응답을 ApiResponse 형태로 반환한다.

     * @param ec  에러 코드 정보
     * @param msg 사용자에게 전달할 메시지
     * @param <T> 응답 데이터 타입 (실패 응답에서는 사용되지 않음)
     * @return 실패 상태를 포함한 ApiResponse 객체
     */
    public static <T> ApiResponse<T> fail(ErrorCode ec, String msg) {
        return new ApiResponse<>(false, null, null,
                ErrorResponse.of(ec.code, msg));
    }
}

