package org.sopt.domain.article.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.global.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum ArticleDomainErrorCode implements ErrorCode {
    ARTICLE_NOT_FOUND(404, 3000, "해당하는 게시글을 찾을 수 없습니다."),
    ALREADY_EXIST_TITLE(400, 3001, "이미 존재하는 게시글 제목입니다.");

    private final int statusCode;
    private final int exceptionCode;
    private final String message;

}
