package org.sopt.domain.article.exception;

import org.sopt.global.exception.BaseException;

public class ArticleDomainException extends BaseException{
    public ArticleDomainException(ArticleDomainErrorCode errorCode) {
        super(errorCode);
    }
}
