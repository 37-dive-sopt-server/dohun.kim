package org.sopt.member.exception;

import org.sopt.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum MemberValidationErrorCode implements ErrorCode {
    EMPTY_NAME(HttpStatus.BAD_REQUEST, 1000, "이름을 입력해주세요."),
    EMPTY_EMAIL(HttpStatus.BAD_REQUEST, 1001, "이메일을 입력해주세요."),
    EMPTY_BIRTHDATE(HttpStatus.BAD_REQUEST, 1002, "생년월일을 입력해주세요."),
    EMPTY_GENDER(HttpStatus.BAD_REQUEST, 1003, "성별을 입력해주세요."),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, 1004, "이메일 형식이 올바르지 않습니다."),
    INVALID_NAME_FORMAT(HttpStatus.BAD_REQUEST, 1005, "이름은 숫자와 영어, 한글로만 구성될 수 있습니다."),
    INVALID_BIRTHDATE(HttpStatus.BAD_REQUEST, 1006, "생년월일이 현재 날짜 이후일 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final int exceptionCode;
    private final String message;

    MemberValidationErrorCode(HttpStatus httpStatus, int exceptionCode, String message) {
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
