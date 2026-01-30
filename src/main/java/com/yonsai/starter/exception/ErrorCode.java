package com.yonsai.starter.exception;

/**
 *  공통 에러 코드 정의
 * - HTTP status : 응답 상태 코드
 * - code        : 내부 에러 식별 코드
 * - msg         : 클라이언트에 전달할 메시지
 */
public enum ErrorCode {
    BAD_REQUEST(400, "BAD_REQUEST", "요청이 올바르지 않습니다."),
    UNAUTHORIZED(401, "UNAUTHORIZED", "인증이 필요합니다."),
    FORBIDDEN(403, "FORBIDDEN", "권한이 없습니다."),
    NOT_FOUND(404, "NOT_FOUND", "페이지를 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(405, "METHOD_NOT_ALLOWED", "허용되지 않은 요청 방식입니다."),
    CONFLICT(409, "CONFLICT", "이미 존재합니다."),
    VALIDATION_ERROR(422, "VALIDATION_ERROR", "입력값이 올바르지 않습니다."),
    TOO_MANY_REQUESTS(429, "TOO_MANY_REQUESTS", "요청이 너무 많습니다."),
    INTERNAL_ERROR(500, "INTERNAL_ERROR", "서버 내부 오류가 발생했습니다.");

    public final int status;
    public final String code;
    public final String msg;

    ErrorCode(int status, String code, String msg) {
        this.status = status;
        this.code = code;
        this.msg = msg;
    }
}
