package com.yonsai.starter.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * API 전역 예외 처리 클래스
 * - 컨트롤러에서 발생하는 예외를 공통 처리한다.
 * - 예외를 HTTP 응답 형태로 변환한다.
 * - 예외 종류에 따라 상태 코드와 메시지를 정의한다.
 * 예외를 직접 던지는 경우 직접 메시지를 지정할 수 있다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 비즈니스 예외 처리
     * ErrorCode에 정의된 상태 코드와 메시지를 반환한다.
     *
     * @param e BusinessException
     * @return 설정한 상태코드 + 에러 메시지
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        ErrorCode ec = e.getErrorCode();
        return ResponseEntity.status(ec.status)
                .body(ErrorResponse.of(ec.code, ec.msg));
    }

    /**
     * 잘못된 요청 파라미터 예외 처리
     * 클라이언트 입력 오류로 간주하여 400 응답을 반환한다
     *
     * @param e IllegalArgumentException
     * @return 400 에러 + 에러 메시지
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException e) {
        ErrorCode ec = ErrorCode.BAD_REQUEST;
        return ResponseEntity.status(ec.status)
                .body(ErrorResponse.of(ec.code , e.getMessage()));
    }

    // TODO: @Valid 검증 실패 예외 처리 추가

    /**
     * 처리되지 않은 모든 예외에 대한 기본 핸들러
     * 서버 내부 오류로 간주하고 500 응답을 반환한다.
     *
     * @param e 에상하지 못 한 모든 예외
     * @return 500 에러 + 에러 메시지
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception e) {
        ErrorCode ec = ErrorCode.INTERNAL_ERROR;
        return ResponseEntity.status(ec.status)
                .body(ErrorResponse.of(ec.code, ec.msg));
    }

}
