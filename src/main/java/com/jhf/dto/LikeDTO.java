package com.jhf.dto;
//TODO: add create_at, it's added as null in db
public class LikeDTO {
    private String username;
    private Integer postId;

    public LikeDTO() {
    }

    public LikeDTO(String username, Integer postId) {
        this.username = username;
        this.postId = postId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }
}
