package org.sopt.exception;

public enum MemberErrorCode implements ErrorCode {
    INVALID_EMAIL_FORMAT(1001, "이메일 형식이 올바르지 않습니다."),
    DUPLICATE_EMAIL(1002, "이미 존재하는 이메일입니다."),
    MEMBER_NOT_FOUND(1003, "⚠️ 해당 ID의 회원을 찾을 수 없습니다."),
    EMPTY_NAME(1004, "⚠️ 이름을 입력해주세요."),
    EMPTY_BIRTHDATE(1005, "⚠️ 생년월일을 입력해주세요."),
    INVALID_BIRTHDATE_FORMAT(1006, "⚠️ 생년월일 형식이 올바르지 않습니다. (예: 2000-05-02)"),
    EMPTY_EMAIL(1007, "⚠️ 이메일을 입력해주세요."),
    INVALID_GENDER_INPUT(1008, "⚠️ 성별을 올바르게 입력해주세요 (MALE/FEMALE/OTHER)"),
    INVALID_ID_FORMAT(1009, "❌ 유효하지 않은 ID 형식입니다. 숫자만 입력해주세요."),
    MEMBER_REGISTRATION_FAILED(1010, "❌ 회원 등록 실패"),
    UNDERAGE_MEMBER(1011, "⚠ 20세 미만은 가입할 수 없습니다.")
    ;


    private final int exceptionCode;
    private final String message;

    MemberErrorCode(int exceptionCode, String message) {
        this.exceptionCode = exceptionCode;
        this.message = message;
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
