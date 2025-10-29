package org.sopt.member.exception;

import org.sopt.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum MemberDomainErrorCode implements ErrorCode {
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, 1002, "이미 존재하는 이메일입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, 1003, "해당 ID의 회원을 찾을 수 없습니다."),
    UNDERAGE_MEMBER(HttpStatus.FORBIDDEN, 1011, "20세 미만은 가입할 수 없습니다."),
    MEMBER_REGISTRATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 1010, "회원 등록 실패");

    private final HttpStatus httpStatus;
    private final int exceptionCode;
    private final String message;

    MemberDomainErrorCode(HttpStatus httpStatus, int exceptionCode, String message) {
        this.httpStatus = httpStatus;
        this.exceptionCode = exceptionCode;
        this.message = message;
    }

    @Override
    public int getStatusCode() {
        return httpStatus.value();
    }

    @Override
    public int getExceptionCode() {
        return exceptionCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
