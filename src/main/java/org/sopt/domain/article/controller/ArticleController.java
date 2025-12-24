package org.sopt.domain.article.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.article.dto.request.ArticleCreateRequest;
import org.sopt.domain.article.dto.request.ArticleSearchRequest;
import org.sopt.domain.article.dto.response.ArticleListResponse;
import org.sopt.domain.article.dto.response.ArticleResponse;
import org.sopt.domain.article.service.ArticleService;
import org.sopt.domain.comment.dto.request.CommentCreateRequest;
import org.sopt.domain.comment.dto.request.CommentDeleteRequest;
import org.sopt.domain.comment.dto.request.CommentUpdateRequest;
import org.sopt.domain.comment.dto.response.CommentListResponse;
import org.sopt.domain.comment.dto.response.CommentResponse;
import org.sopt.global.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<ArticleListResponse>> searchArticles(
            @ModelAttribute ArticleSearchRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(articleService.searchArticles(request)));
    }

    @PostMapping("/{articleId}/comments")
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(
            @PathVariable(name = "articleId") Long articleId,
            @RequestBody CommentCreateRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(articleService.createComment(articleId, request)));
    }

    @PatchMapping("/{articleId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponse>> updateComment(
            @PathVariable(name = "articleId") Long articleId,
            @PathVariable(name = "commentId") Long commentId,
            @RequestBody CommentUpdateRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(articleService.updateComment(articleId, commentId, request)));
    }

    @DeleteMapping("/{articleId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @PathVariable(name = "articleId") Long articleId,
            @PathVariable(name = "commentId") Long commentId,
            @RequestBody CommentDeleteRequest request
    ) {
        articleService.deleteComment(articleId, commentId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok());
    }

    @GetMapping("/{articleId}/comments")
    public ResponseEntity<ApiResponse<CommentListResponse>> getComments(
            @PathVariable(name = "articleId") Long articleId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(articleService.getArticleComments(articleId)));
    }

    @GetMapping("/{articleId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponse>> getComment(
            @PathVariable(name = "articleId") Long articleId,
            @PathVariable(name = "commentId") Long commentId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(articleService.getArticleCommentDetail(articleId, commentId)));
    }
}
