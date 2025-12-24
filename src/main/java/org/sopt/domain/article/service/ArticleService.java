package org.sopt.domain.article.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.article.dto.request.ArticleCreateRequest;
import org.sopt.domain.article.dto.request.ArticleSearchRequest;
import org.sopt.domain.article.dto.response.ArticleListResponse;
import org.sopt.domain.article.dto.response.ArticleResponse;
import org.sopt.domain.article.entity.Article;
import org.sopt.domain.article.exception.ArticleDomainErrorCode;
import org.sopt.domain.article.exception.ArticleDomainException;
import org.sopt.domain.article.repository.ArticleRepository;
import org.sopt.domain.comment.dto.request.CommentCreateRequest;
import org.sopt.domain.comment.dto.request.CommentDeleteRequest;
import org.sopt.domain.comment.dto.request.CommentUpdateRequest;
import org.sopt.domain.comment.dto.response.CommentListResponse;
import org.sopt.domain.comment.dto.response.CommentResponse;
import org.sopt.domain.comment.entity.Comment;
import org.sopt.domain.comment.exception.CommentDomainErrorCode;
import org.sopt.domain.comment.exception.CommentDomainException;
import org.sopt.domain.comment.repository.CommentRepository;
import org.sopt.domain.member.entity.Member;
import org.sopt.domain.member.exception.MemberDomainErrorCode;
import org.sopt.domain.member.exception.MemberDomainException;
import org.sopt.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ArticleResponse createArticle(ArticleCreateRequest articleCreateRequest) {
        Member author = memberRepository.findById(articleCreateRequest.authorId())
                .orElseThrow(() -> new MemberDomainException(MemberDomainErrorCode.MEMBER_NOT_FOUND));

        if (articleRepository.existsByTitle(articleCreateRequest.title())) {
            throw new ArticleDomainException(ArticleDomainErrorCode.ALREADY_EXIST_TITLE);
        }

        Article savedArticle = articleRepository.save(Article.of(articleCreateRequest, author));

        return savedArticle.toArticleResponse();
    }

    public ArticleResponse getArticleById(Long articleId) {
        Article article = getArticleWithComments(articleId);
        return article.toArticleResponse();
    }

    public ArticleListResponse getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return new ArticleListResponse(
                articles.stream()
                        .map(Article::toArticleResponse)
                        .toList()
        );
    }

    public ArticleListResponse searchArticles(ArticleSearchRequest request) {
        String title = request.title();
        String authorName = request.authorName();

        List<Article> articles;

        if (title != null && authorName != null) {
            articles = articleRepository.findByTitleContainingIgnoreCaseAndAuthor_NameContainingIgnoreCase(title, authorName);
        } else if (title != null) {
            articles = articleRepository.findByTitleContainingIgnoreCase(title);
        } else if (authorName != null) {
            articles = articleRepository.findByAuthor_NameContainingIgnoreCase(authorName);
        } else {
            articles = articleRepository.findAll();
        }

        return new ArticleListResponse(
                articles.stream()
                        .map(Article::toArticleResponse)
                        .toList()
        );
    }

    @Transactional
    public CommentResponse createComment(Long articleId, CommentCreateRequest request) {
        Article article = getArticle(articleId);
        Member author = getMember(request.memberId());
        Comment comment = Comment.of(article, author, request.content());
        article.addComment(comment);
        Comment saved = commentRepository.save(comment);
        return saved.toCommentResponse();
    }

    @Transactional
    public CommentResponse updateComment(Long articleId, Long commentId, CommentUpdateRequest request) {
        Comment comment = getArticleComment(articleId, commentId);
        validateAuthor(comment, request.memberId());
        comment.changeContent(request.content());
        return comment.toCommentResponse();
    }

    @Transactional
    public void deleteComment(Long articleId, Long commentId, CommentDeleteRequest request) {
        Comment comment = getArticleComment(articleId, commentId);
        validateAuthor(comment, request.memberId());
        comment.getArticle().removeComment(comment);
        commentRepository.delete(comment);
    }

    public CommentListResponse getArticleComments(Long articleId) {
        Article article = getArticleWithComments(articleId);
        List<CommentResponse> comments = article.getComments().stream()
                .map(Comment::toCommentResponse)
                .toList();
        return new CommentListResponse(comments);
    }

    public CommentResponse getArticleCommentDetail(Long articleId, Long commentId) {
        Comment comment = getArticleComment(articleId, commentId);
        return comment.toCommentResponse();
    }

    private Article getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleDomainException(ArticleDomainErrorCode.ARTICLE_NOT_FOUND));
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberDomainException(MemberDomainErrorCode.MEMBER_NOT_FOUND));
    }

    private Article getArticleWithComments(Long articleId) {
        return articleRepository.findWithCommentsById(articleId)
                .orElseThrow(() -> new ArticleDomainException(ArticleDomainErrorCode.ARTICLE_NOT_FOUND));
    }

    private Comment getArticleComment(Long articleId, Long commentId) {
        return commentRepository.findByIdAndArticle_Id(commentId, articleId)
                .orElseThrow(() -> new CommentDomainException(CommentDomainErrorCode.COMMENT_NOT_FOUND));
    }

    private void validateAuthor(Comment comment, Long memberId) {
        if (memberId == null || !comment.getAuthor().getId().equals(memberId)) {
            throw new CommentDomainException(CommentDomainErrorCode.COMMENT_AUTHOR_MISMATCH);
        }
    }
}
