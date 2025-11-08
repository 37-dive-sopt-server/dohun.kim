package org.sopt.domain.article.dto.response;

import java.time.LocalDateTime;
import org.sopt.domain.article.entity.ArticleTag;

public record ArticleResponse(
        Long id,
        Long authorId,
        String title,
        String content,
        ArticleTag tag,
        LocalDateTime createdAt
) {
}
