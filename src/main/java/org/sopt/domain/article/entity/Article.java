package org.sopt.domain.article.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.domain.article.dto.request.ArticleCreateRequest;
import org.sopt.domain.article.dto.response.ArticleResponse;
import org.sopt.domain.comment.dto.response.CommentResponse;
import org.sopt.domain.comment.entity.Comment;
import org.sopt.domain.member.entity.Member;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member author;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ArticleTag tag;

    @Column(nullable = false, unique = true, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Comment> comments = new ArrayList<>();

    public static Article of(ArticleCreateRequest request, Member author) {
        return Article.builder()
                .author(author)
                .tag(request.tag())
                .title(request.title())
                .content(request.content())
                .build();
    }

    public ArticleResponse toArticleResponse() {
        List<CommentResponse> commentResponses = this.comments.stream()
                .map(Comment::toCommentResponse)
                .toList();

        return new ArticleResponse(
                this.id,
                this.author.getId(),
                this.title,
                this.content,
                this.tag,
                this.createdAt,
                commentResponses
        );
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.assignArticle(this);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
    }

    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }
}
