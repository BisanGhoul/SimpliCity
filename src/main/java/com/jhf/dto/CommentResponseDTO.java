package com.jhf.dto;

import java.time.Instant;
import java.util.List;

public class CommentResponseDTO {

    private Integer id;
    private String content;
    private String picUrl;
    private Instant createdAt;
    private Instant updatedAt;
    private String username;
    private Integer parentCommentId;
    private Integer postId;
    private List<CommentResponseDTO> childComments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Integer parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public List<CommentResponseDTO> getChildComments() {
        return childComments;
    }

    public void setChildComments(List<CommentResponseDTO> childComments) {
        this.childComments = childComments;
    }
}
