package org.sopt.domain.article.dto.request;

import org.sopt.domain.article.entity.ArticleTag;

public record ArticleCreateRequest(
        Long authorId,
        String title,
        String content,
        ArticleTag tag
) {
}
