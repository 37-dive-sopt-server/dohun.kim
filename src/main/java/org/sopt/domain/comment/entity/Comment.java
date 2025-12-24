package org.sopt.domain.comment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.domain.article.entity.Article;
import org.sopt.domain.comment.dto.response.CommentResponse;
import org.sopt.domain.comment.exception.CommentDomainErrorCode;
import org.sopt.domain.comment.exception.CommentDomainException;
import org.sopt.domain.member.entity.Member;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Comment {

    private static final int MAX_CONTENT_LENGTH = 300;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member author;

    @Column(nullable = false, length = 300)
    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static Comment of(Article article, Member author, String content) {
        validateContent(content);
        return Comment.builder()
                .article(article)
                .author(author)
                .content(content)
                .build();
    }

    public void changeContent(String content) {
        validateContent(content);
        this.content = content;
    }

    public void assignArticle(Article article) {
        this.article = article;
    }

    public CommentResponse toCommentResponse() {
        return CommentResponse.from(this);
    }

    private static void validateContent(String content) {
        if (content == null || content.isBlank()) {
            throw new CommentDomainException(CommentDomainErrorCode.COMMENT_CONTENT_REQUIRED);
        }

        if (content.length() > MAX_CONTENT_LENGTH) {
            throw new CommentDomainException(CommentDomainErrorCode.INVALID_COMMENT_CONTENT_LENGTH);
        }
    }
}
