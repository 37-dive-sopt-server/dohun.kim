package org.sopt.member.exception;

import org.sopt.global.exception.BaseException;

public class MemberException extends BaseException {
    public MemberException(MemberErrorCode errorCode) {
        super(errorCode);
    }
}
