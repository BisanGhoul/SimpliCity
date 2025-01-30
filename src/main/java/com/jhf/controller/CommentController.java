package com.jhf.controller;

import com.jhf.dto.CommentRequestDTO;
import com.jhf.dto.CommentResponseDTO;
import com.jhf.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // Add a new comment
    @PostMapping
    public ResponseEntity<CommentResponseDTO> addComment(@Valid @RequestBody CommentRequestDTO requestDTO) {
        CommentResponseDTO createdComment = commentService.addComment(requestDTO);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    // Edit an existing comment
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDTO> editComment(
            @PathVariable Integer commentId,
            @RequestParam String newContent) {
        CommentResponseDTO updatedComment = commentService.editComment(commentId, newContent);
        return ResponseEntity.ok(updatedComment);
    }

    // Delete a comment
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    // Get all top-level comments for a post
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponseDTO>> getTopLevelCommentsForPost(@PathVariable Integer postId) {
        List<CommentResponseDTO> comments = commentService.getTopLevelCommentsForPost(postId);
        return ResponseEntity.ok(comments);
    }
}
