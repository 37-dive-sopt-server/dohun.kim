package org.sopt.exception;

import org.springframework.http.HttpStatus;

public enum MemberErrorCode implements ErrorCode {
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST.value(), 1001, "⚠️ 이메일 형식이 올바르지 않습니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT.value(), 1002, "⚠️ 이미 존재하는 이메일입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 1003, "⚠️ 해당 ID의 회원을 찾을 수 없습니다."),
    EMPTY_NAME(HttpStatus.BAD_REQUEST.value(), 1004, "⚠️ 이름을 입력해주세요."),
    EMPTY_BIRTHDATE(HttpStatus.BAD_REQUEST.value(), 1005, "⚠️ 생년월일을 입력해주세요."),
    INVALID_BIRTHDATE_FORMAT(HttpStatus.BAD_REQUEST.value(), 1006, "⚠️ 생년월일 형식이 올바르지 않습니다. (예: 2000-05-02)"),
    EMPTY_EMAIL(HttpStatus.BAD_REQUEST.value(), 1007, "⚠️ 이메일을 입력해주세요."),
    INVALID_GENDER_INPUT(HttpStatus.BAD_REQUEST.value(), 1008, "⚠️ 성별을 올바르게 입력해주세요 (MALE/FEMALE/OTHER)"),
    INVALID_ID_FORMAT(HttpStatus.BAD_REQUEST.value(), 1009, "❌ 유효하지 않은 ID 형식입니다. 숫자만 입력해주세요."),
    MEMBER_REGISTRATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), 1010, "❌ 회원 등록 실패"),
    UNDERAGE_MEMBER(HttpStatus.FORBIDDEN.value(), 1011, "⚠️ 20세 미만은 가입할 수 없습니다."),
    INVALID_NAME_FORMAT(HttpStatus.BAD_REQUEST.value(), 1013, "⚠️ 이름은 숫자와 영어, 한글로만 구성될 수 있습니다."),
    ;

    private final int statusCode;
    private final int exceptionCode;
    private final String message;

    MemberErrorCode(int statusCode, int exceptionCode, String message) {
        this.statusCode = statusCode;
        this.exceptionCode = exceptionCode;
        this.message = message;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
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
