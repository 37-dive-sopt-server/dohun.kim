package org.sopt.member.exception;

import org.sopt.global.exception.BaseException;

public class MemberDomainException extends BaseException {

    public MemberDomainException(MemberDomainErrorCode errorCode) {
        super(errorCode);
    }
}
