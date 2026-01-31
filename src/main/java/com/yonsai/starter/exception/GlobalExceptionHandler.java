package com.yonsai.starter.exception;

import com.yonsai.starter.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {
        ErrorCode ec = e.getErrorCode();

        return ResponseEntity.status(ec.status)
                .body(ApiResponse.fail(ec, ec.msg));
    }


    /**
     * 잘못된 요청 파라미터 예외 처리
     * 클라이언트 입력 오류로 간주하여 400 응답을 반환한다
     *
     * @param e IllegalArgumentException
     * @return 400 에러 + 에러 메시지
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>>  handleBadRequest(IllegalArgumentException e) {
        ErrorCode ec = ErrorCode.BAD_REQUEST;
        return ResponseEntity.status(ec.status)
                .body(ApiResponse.fail(ec, ec.msg));
    }

    /**
     * @Valid 검증 실패 예외 처리
     * DTO 유효성 검사 실패 시 발생하는 MethodArgumentNotValidException을 처리한다.
     * 첫 번째 필드 에러 메시지를 추출하여 400(BAD_REQUEST) 응답으로 반환한다.
     *
     * 사용 방법:
     * - 컨트롤러 메서드 파라미터에 @Valid를 붙여 DTO를 전달받는다
     * - DTO(record/class)에 검증 어노테이션을 작성한다
     *
     * 예시:
     *   @NotBlank(message = "필수 입력값입니다")
     *
     * @param e 검증 실패 예외
     * @return 400 상태 코드와 검증 메시지를 포함한 ErrorResponse
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>>  handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorCode ec = ErrorCode.BAD_REQUEST;

        // 여러 에러가 터졌을 경우 하나의 예외 메세지만 전달한다.
        // 가장 먼저 고쳐야 할 에러 하나만 알려준다
        String message = e.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity.status(ec.status)
                .body(ApiResponse.fail(ec, ec.msg));
    }
    /**
     * 처리되지 않은 모든 예외에 대한 기본 핸들러
     * 서버 내부 오류로 간주하고 500 응답을 반환한다.
     *
     * @param e 에상하지 못 한 모든 예외
     * @return 500 에러 + 에러 메시지
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleAllExceptions(Exception e) {
        ErrorCode ec = ErrorCode.INTERNAL_ERROR;
        return ResponseEntity.status(ec.status)
                .body(ApiResponse.fail(ec, ec.msg));
    }

}
