package org.sopt.domain.comment.exception;

import org.sopt.global.exception.BaseException;

public class CommentDomainException extends BaseException {
    public CommentDomainException(CommentDomainErrorCode errorCode) {
        super(errorCode);
    }
}
