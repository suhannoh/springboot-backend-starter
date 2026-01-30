package com.yonsai.starter.exception;

/**
 * 비즈니스 로직에서 사용하는 사용자 정의 예외
 * - ErrorCode를 포함한다
 * - API 에러 응답과 직접 연결된다
 * - 필요 시 커스텀 메시지를 전달할 수 있다
 */
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;

    /**
     * 기본 메세지를 전달하는 생성
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.msg);
        this.errorCode = errorCode;
    }

    /**
     * 커스텀 메세지를 전달하는 생성
     */
    public BusinessException(ErrorCode errorCode , String customMsg){
        super(customMsg);
        this.errorCode = errorCode;
    }

    /**
     * 사용자 예외에 대한 에러 코드를 반환한다.
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
