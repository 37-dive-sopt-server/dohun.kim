package org.sopt.domain.article.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import org.sopt.domain.article.entity.ArticleTag;
import org.sopt.domain.comment.dto.response.CommentResponse;

public record ArticleResponse(
        Long id,
        Long authorId,
        String title,
        String content,
        ArticleTag tag,
        LocalDateTime createdAt,
        List<CommentResponse> comments
) {
}
