package org.sopt.domain.article.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.article.dto.request.ArticleCreateRequest;
import org.sopt.domain.article.dto.response.ArticleListResponse;
import org.sopt.domain.article.dto.response.ArticleResponse;
import org.sopt.domain.article.service.ArticleService;
import org.sopt.global.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<ApiResponse<ArticleResponse>> createArticle(
            @RequestBody ArticleCreateRequest articleCreateRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(articleService.createArticle(articleCreateRequest)));
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<ApiResponse<ArticleResponse>> getArticleById(
            @PathVariable(name = "articleId") Long articleId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(articleService.getArticleById(articleId)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ArticleListResponse>> getAllArticles() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(articleService.getAllArticles()));
    }
}
