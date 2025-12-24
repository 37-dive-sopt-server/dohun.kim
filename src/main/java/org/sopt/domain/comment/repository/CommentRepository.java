package org.sopt.domain.comment.repository;

import java.util.Optional;
import org.sopt.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @EntityGraph(attributePaths = "author")
    Optional<Comment> findByIdAndArticle_Id(Long id, Long articleId);
}
