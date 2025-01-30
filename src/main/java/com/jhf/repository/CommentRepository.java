package com.jhf.repository;

import com.jhf.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    // Fetch all comments for a post (top-level and nested)
    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.comments WHERE c.post.id = :postId AND c.parentComment IS NULL")
    List<Comment> findTopLevelCommentsByPostId(@Param("postId") Integer postId);

    // Fetch all child comments for a parent comment
    @Query("SELECT c FROM Comment c WHERE c.parentComment.id = :parentId")
    List<Comment> findChildCommentsByParentId(@Param("parentId") Integer parentId);

    // Count comments for a specific post
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.post.id = :postId")
    Long countCommentsByPostId(Integer postId);
}
