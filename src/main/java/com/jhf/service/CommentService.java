package com.jhf.service;

import com.jhf.dto.CommentRequestDTO;
import com.jhf.dto.CommentResponseDTO;
import com.jhf.models.Comment;
import com.jhf.models.Post;
import com.jhf.models.User;
import com.jhf.repository.CommentRepository;
import com.jhf.repository.PostRepository;
import com.jhf.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // Add a new comment
    public CommentResponseDTO addComment(CommentRequestDTO requestDTO) {
        User user = userRepository.findById(requestDTO.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + requestDTO.getUsername()));

        Post post = postRepository.findById(requestDTO.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + requestDTO.getPostId()));

        Comment parentComment = null;
        if (requestDTO.getParentCommentId() != null) {
            parentComment = commentRepository.findById(requestDTO.getParentCommentId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent comment not found with id: " + requestDTO.getParentCommentId()));
        }

        Comment comment = new Comment();
        comment.setContent(requestDTO.getContent());
        comment.setUsername(user);
        comment.setPost(post);
        comment.setParentComment(parentComment);

        Comment savedComment = commentRepository.save(comment);
        return toResponseDTO(savedComment);
    }

    // Edit an existing comment
    public CommentResponseDTO editComment(Integer commentId, String newContent) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));

        comment.setContent(newContent);
        Comment updatedComment = commentRepository.save(comment);
        return toResponseDTO(updatedComment);
    }

    // Delete a comment
    public void deleteComment(Integer commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new EntityNotFoundException("Comment not found with id: " + commentId);
        }
        commentRepository.deleteById(commentId);
    }

    // Get all comments for a post
    public List<CommentResponseDTO> getTopLevelCommentsForPost(Integer postId) {
        List<Comment> comments = commentRepository.findTopLevelCommentsByPostId(postId);
        return comments.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    // Convert Entity to Response DTO
    private CommentResponseDTO toResponseDTO(Comment comment) {
        CommentResponseDTO responseDTO = new CommentResponseDTO();
        responseDTO.setId(comment.getId());
        responseDTO.setContent(comment.getContent());
        responseDTO.setPicUrl(comment.getPicUrl());
        responseDTO.setCreatedAt(comment.getCreatedAt());
        responseDTO.setUpdatedAt(comment.getUpdatedAt());
        responseDTO.setUsername(comment.getUsername().getUsername());
        responseDTO.setParentCommentId(comment.getParentComment() != null ? comment.getParentComment().getId() : null);
        responseDTO.setPostId(comment.getPost().getId());
        responseDTO.setChildComments(comment.getComments().stream().map(this::toResponseDTO).collect(Collectors.toList()));
        return responseDTO;
    }
}
