package org.sopt.domain.article.dto.request;

public record ArticleSearchRequest(
        String title,
        String authorName
) {
}
