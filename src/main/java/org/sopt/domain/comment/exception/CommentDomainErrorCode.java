package org.sopt.domain.comment.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.global.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum CommentDomainErrorCode implements ErrorCode {
    COMMENT_NOT_FOUND(404, 4000, "해당 댓글을 찾을 수 없습니다."),
    COMMENT_AUTHOR_MISMATCH(403, 4001, "댓글 작성자만 수정 또는 삭제할 수 있습니다."),
    INVALID_COMMENT_CONTENT_LENGTH(400, 4002, "댓글은 300자 이내로 작성해야 합니다."),
    COMMENT_CONTENT_REQUIRED(400, 4003, "댓글 내용을 입력해야 합니다.");

    private final int statusCode;
    private final int exceptionCode;
    private final String message;
}
