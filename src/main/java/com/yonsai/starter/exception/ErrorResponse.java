package com.yonsai.starter.exception;

import java.time.LocalDateTime;

/**
 * 예외 응답 전달 DTO
 *  - JSON 형태로 응답한다.
 *  - 상태코드 , 에러메세지 , 에러 생성 시간을 전달한다.
 *
 *  - ErrorResponse는 예외 발생시에 ApiResponse 필드에 담겨 전달된다
 *
 * @param code 에러코드
 * @param msg 에러 메시지
 * @param timeStamp 에러 발생 시간
 */
public record ErrorResponse(
        String code,
        String msg,
        LocalDateTime timeStamp
) {
    /**
     * 예외 응답 DTO를 생성한다.
     *  - GlobalExceptionHandler에서 예외를 잡고 전달할 때 생성한다.
     * @param code 에러코드
     * @param msg 에러 메시지
     * @return ErrorResponse
     */
    public static ErrorResponse of(String code, String msg) {
        return new ErrorResponse(code, msg , LocalDateTime.now());
    }
}
