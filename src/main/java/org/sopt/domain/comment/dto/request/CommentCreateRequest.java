package org.sopt.domain.comment.dto.request;

public record CommentCreateRequest(
        Long memberId,
        String content
) {
}
