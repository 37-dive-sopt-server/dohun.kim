package org.sopt.domain.article.repository;

import java.util.List;
import org.sopt.domain.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    boolean existsByTitle(String title);

    List<Article> findByTitleContainingIgnoreCase(String title);
    List<Article> findByAuthor_NameContainingIgnoreCase(String authorName);
    List<Article> findByTitleContainingIgnoreCaseAndAuthor_NameContainingIgnoreCase(String title, String authorName);
}
