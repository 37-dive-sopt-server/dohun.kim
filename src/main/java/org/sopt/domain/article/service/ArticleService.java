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
import org.sopt.domain.member.entity.Member;
import org.sopt.domain.member.exception.MemberDomainErrorCode;
import org.sopt.domain.member.exception.MemberDomainException;
import org.sopt.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

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
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleDomainException(ArticleDomainErrorCode.ARTICLE_NOT_FOUND));
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
}
