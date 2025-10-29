package org.sopt.member.exception;

import org.sopt.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum MemberValidationErrorCode implements ErrorCode {
    EMPTY_NAME(HttpStatus.BAD_REQUEST, 1004, "⚠️ 이름을 입력해주세요."),
    EMPTY_EMAIL(HttpStatus.BAD_REQUEST, 1007, "⚠️ 이메일을 입력해주세요."),
    EMPTY_BIRTHDATE(HttpStatus.BAD_REQUEST, 1005, "⚠️ 생년월일을 입력해주세요."),
    EMPTY_GENDER(HttpStatus.BAD_REQUEST, 1013, "⚠️ 성별을 입력해주세요."),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, 1001, "⚠️ 이메일 형식이 올바르지 않습니다."),
    INVALID_NAME_FORMAT(HttpStatus.BAD_REQUEST, 1012, "⚠️ 이름은 숫자와 영어, 한글로만 구성될 수 있습니다."),
    INVALID_BIRTHDATE(HttpStatus.BAD_REQUEST, 1014, "⚠️ 생년월일이 현재 날짜 이후일 수 없습니다."),
    INVALID_BIRTHDATE_FORMAT(HttpStatus.BAD_REQUEST, 1006, "⚠️ 생년월일 형식이 올바르지 않습니다. (예: 2000-05-02)"),
    INVALID_GENDER_INPUT(HttpStatus.BAD_REQUEST, 1008, "⚠️ 성별을 올바르게 입력해주세요 (MALE/FEMALE/OTHER)"),
    INVALID_ID_FORMAT(HttpStatus.BAD_REQUEST, 1009, "❌ 유효하지 않은 ID 형식입니다. 숫자만 입력해주세요.");

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
