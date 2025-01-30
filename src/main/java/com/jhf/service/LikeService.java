package com.jhf.service;

import com.jhf.dto.LikeDTO;
import com.jhf.models.Like;
import com.jhf.models.Post;
import com.jhf.models.User;
import com.jhf.repository.LikeRepository;
import com.jhf.repository.PostRepository;
import com.jhf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public void addLike(LikeDTO likeDTO) {
        // Check if the user and post exist
        User user = userRepository.findByUsername(likeDTO.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Post post = postRepository.findById(likeDTO.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        // Check if the user has already liked the post
        if (likeRepository.existsByUsername_UsernameAndPost_Id(user.getUsername(), post.getId())) {
            throw new IllegalArgumentException("User has already liked this post");
        }

        // Add the like
        Like like = new Like();
        like.setUsername(user);
        like.setPost(post);
        likeRepository.save(like);

        // Update like count in the Post
        post.setLikeCount(post.getLikeCount() + 1);
        postRepository.save(post);
    }

    public void deleteLike(LikeDTO likeDTO) {
        // Check if the user and post exist
        User user = userRepository.findByUsername(likeDTO.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Post post = postRepository.findById(likeDTO.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        // Check if the like exists
        if (!likeRepository.existsByUsername_UsernameAndPost_Id(user.getUsername(), post.getId())) {
            throw new IllegalArgumentException("Like does not exist");
        }

        // Delete the like
        likeRepository.deleteByUsername_UsernameAndPost_Id(user.getUsername(), post.getId());

        // Update like count in the Post
        post.setLikeCount(post.getLikeCount() - 1);
        postRepository.save(post);
    }
}
