package com.jhf.service;

import com.jhf.dto.PostDTO;
import com.jhf.dto.CommentResponseDTO;
import com.jhf.dto.LikeDTO;
import com.jhf.dto.UserBasicInfoDTO;
import com.jhf.models.Post;
import com.jhf.models.User;
import com.jhf.models.UserRelationship;
import com.jhf.repository.LikeRepository;
import com.jhf.repository.PostRepository;
import com.jhf.repository.UserRelationshipRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final UserRelationshipRepository userRelationshipRepository;

    private final UserService userService;
    private final CommentService commentService;
    private final LikeService likeService;

    public PostService(PostRepository postRepository, LikeRepository likeRepository, UserRelationshipRepository userRelationshipRepository, UserService userService, CommentService commentService, LikeService likeService) {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.userRelationshipRepository = userRelationshipRepository;
        this.userService = userService;
        this.commentService = commentService;
        this.likeService = likeService;
    }

    // Add a post
    public PostDTO addPost(PostDTO postDTO) {
        Post post = mapToEntity(postDTO);
        Post savedPost = postRepository.save(post);
        return mapToDTO(savedPost);
    }

    // Edit a post
    public PostDTO editPost(Integer postId, PostDTO postDTO) {
        // Retrieve the existing post
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            throw new IllegalArgumentException("Post not found with id: " + postId);
        }
        Post post = optionalPost.get();

        // Update only non-null fields
        if (postDTO.getContent() != null) {
            post.setContent(postDTO.getContent());
        }
        if (postDTO.getPicUrl() != null) {
            post.setPicUrl(postDTO.getPicUrl());
        }
        if (postDTO.getStatus() != null) {
            post.setStatus(postDTO.getStatus());
        }

        // Save the updated post and map it to DTO
        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }


    // Delete a post
    public void deletePost(Integer postId) {
        if (!postRepository.existsById(postId)) {
            throw new IllegalArgumentException("Post not found with id: " + postId);
        }
        postRepository.deleteById(postId);
    }

    // Get all posts for a user
    public List<PostDTO> getAllPostsForUser(String username) {
        List<Post> posts = postRepository.findAllByUsername_Username(username);
        return posts.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<LikeDTO> getLikesByPostId(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        return likeRepository.findByPost_Id(post.getId())
                .stream()
                .map(like -> new LikeDTO(like.getUsername().getUsername(), like.getPost().getId()))
                .toList();
    }
    public List<PostDTO> getPostsFromFriends(String username) {
        // Get UserBasicInfoDTO object
        UserBasicInfoDTO userBasicInfoDTO = userService.getUserByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found for username: " + username));

        // Convert UserBasicInfoDTO to User entity
        User user = userService.convertToEntity(userBasicInfoDTO);

        // Get all relationships where the user is involved as either first or second user
        List<UserRelationship> friendships = userRelationshipRepository.findByFirstUsernameOrSecondUsername(user, user);

        // Collect friends' usernames
        List<User> friends = friendships.stream()
                .filter(friendship -> friendship.getRelationshipType().equals("friend"))
                .map(friendship -> {
                    if (friendship.getFirstUsername().equals(user)) {
                        return friendship.getSecondUsername();
                    } else {
                        return friendship.getFirstUsername();
                    }
                })
                .collect(Collectors.toList());

        // Retrieve all posts made by the user's friends
        List<Post> posts = postRepository.findByUsernameIn(friends);

        // Map Post entities to PostDTOs
        return posts.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Map Post entity to PostDTO
    private PostDTO mapToDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setContent(post.getContent());
        dto.setPicUrl(post.getPicUrl());
        dto.setLikeCount(post.getLikes().size());
        dto.setStatus(post.getStatus());

        // Fetch user details from UserService
        dto.setOwner(userService.getUserByUsername(post.getUsername().getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found for username: " + post.getUsername().getUsername())));
        // Fetch comments using CommentService
        dto.setComments(commentService.getTopLevelCommentsForPost(post.getId()));

        // Fetch likes using LikeService
        dto.setLikes(getLikesByPostId(post.getId()));

        return dto;
    }

    // Map PostDTO to Post entity
    private Post mapToEntity(PostDTO dto) {
        Post post = new Post();
        post.setContent(dto.getContent());
        post.setPicUrl(dto.getPicUrl());
        post.setStatus(dto.getStatus());

        // Fetch the user using UserService and set it to the Post
        post.setUsername(
                userService.convertToEntity(userService.getUserByUsername(dto.getOwner().getUsername())
                        .orElseThrow(() -> new IllegalArgumentException("User not found for username: " + post.getUsername().getUsername())))
        );

        return post;
    }


}
