package org.sopt.domain.comment.dto.request;

public record CommentUpdateRequest(
        Long memberId,
        String content
) {
}
