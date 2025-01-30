package com.jhf.dto;

import com.jhf.models.Like;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PostDTO {
    private Integer id;

    private UserBasicInfoDTO owner;

    @NotNull
    @Size(max = 2000)
    private String content;

    @Size(max = 255)
    private String picUrl;

    private Integer likeCount;

    private String status;

    private List<CommentResponseDTO> comments = new ArrayList<>();

    private List<LikeDTO> likes = new ArrayList<>();


    public List<LikeDTO> getLikes() {
        return likes;
    }

    public void setLikes(List<LikeDTO> likes) {
        this.likes = likes;
    }

    public List<CommentResponseDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentResponseDTO> comments) {
        this.comments = comments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserBasicInfoDTO getOwner() {
        return owner;
    }

    public void setOwner(UserBasicInfoDTO owner) {
        this.owner = owner;
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

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
