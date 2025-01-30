package com.jhf.controller;

import com.jhf.dto.PostDTO;
import com.jhf.dto.LikeDTO;
import com.jhf.models.Post;
import com.jhf.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // Add a new post
    @PostMapping
    public ResponseEntity<PostDTO> addPost(@RequestBody PostDTO postDTO) {
        PostDTO savedPost = postService.addPost(postDTO);
        return ResponseEntity.ok(savedPost);
    }

    // Edit an existing post
    @PutMapping("/{postId}")
    public ResponseEntity<PostDTO> editPost(@PathVariable Integer postId, @RequestBody PostDTO postDTO) {
        PostDTO updatedPost = postService.editPost(postId, postDTO);
        return ResponseEntity.ok(updatedPost);
    }

    // Delete a post
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    // Get all posts for a user
    @GetMapping("/user/{username}")
    public ResponseEntity<List<PostDTO>> getAllPostsForUser(@PathVariable String username) {
        List<PostDTO> posts = postService.getAllPostsForUser(username);
        return ResponseEntity.ok(posts);
    }

    // Get likes for a specific post
    @GetMapping("/{postId}/likes")
    public ResponseEntity<List<LikeDTO>> getLikesByPostId(@PathVariable Integer postId) {
        List<LikeDTO> likes = postService.getLikesByPostId(postId);
        return ResponseEntity.ok(likes);
    }

    // Endpoint to get all posts from the friends of a specific user
    @GetMapping("/friends/{username}")
    public List<PostDTO> getPostsFromFriends(@PathVariable String username) {
        return postService.getPostsFromFriends(username);
    }
}
