package org.sopt.domain.member.exception;

import org.sopt.global.exception.BaseException;

public class MemberValidationException extends BaseException {

    public MemberValidationException(MemberValidationErrorCode errorCode) {
        super(errorCode);
    }
}
