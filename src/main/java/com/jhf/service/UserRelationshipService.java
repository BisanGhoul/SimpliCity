package com.jhf.service;

import com.jhf.models.User;
import com.jhf.models.UserRelationship;
import com.jhf.repository.UserRelationshipRepository;
import com.jhf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserRelationshipService {

    @Autowired
    private UserRelationshipRepository userRelationshipRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    // Send Friend Request
    public void sendFriendRequest(String firstUsername, String secondUsername) {
        // Fetch users from the database using the usernames
        User firstUser = userRepository.findByUsername(firstUsername)
                .orElseThrow(() -> new IllegalArgumentException("User not found for username: " + firstUsername));
        User secondUser = userRepository.findByUsername(secondUsername)
                .orElseThrow(() -> new IllegalArgumentException("User not found for username: " + secondUsername));

        // Check if the users are trying to send a friend request to themselves
        if (firstUsername.equals(secondUsername)) {
            throw new IllegalArgumentException("Users cannot send a friend request to themselves.");
        }

        // Check if a friendship or pending request already exists
        Optional<UserRelationship> existingRelationship = userRelationshipRepository
                .findByFirstUsernameAndSecondUsername(firstUser, secondUser);

        // If an existing relationship is found
        if (existingRelationship.isPresent()) {
            String relationshipType = existingRelationship.get().getRelationshipType();

            // If it's already a friend, or pending request, return appropriate response
            if ("friend".equals(relationshipType)) {
                throw new IllegalArgumentException("You are already friends.");
            } else if ("pending".equals(relationshipType)) {
                throw new IllegalArgumentException("Friend request is already pending.");
            }
        }

        // Create and save a new UserRelationship (Friend Request)
        UserRelationship userRelationship = new UserRelationship();
        userRelationship.setFirstUsername(firstUser);
        userRelationship.setSecondUsername(secondUser);
        userRelationship.setRelationshipType("pending"); // Mark as pending until accepted

        userRelationshipRepository.save(userRelationship);
    }

    // Accept Friend Request
    @Transactional
    public void acceptFriendRequest(String firstUsername, String secondUsername) {
        User firstUser = userRepository.findByUsername(firstUsername)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + firstUsername));
        User secondUser = userRepository.findByUsername(secondUsername)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + secondUsername));

        UserRelationship relationship = userRelationshipRepository
                .findByFirstUsernameAndSecondUsername(firstUser, secondUser)
                .orElseThrow(() -> new IllegalArgumentException("Friend request not found"));

        if ("pending".equals(relationship.getRelationshipType())) {
            relationship.setRelationshipType("friend");
            userRelationshipRepository.save(relationship);
        } else {
            throw new IllegalStateException("Friend request has already been accepted or is not in 'pending' status");
        }
    }

    // Unfriend
    @Transactional
    public void unfriend(String firstUsername, String secondUsername) {
        // Fetch users from the database
        User firstUser = userRepository.findByUsername(firstUsername)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + firstUsername));
        User secondUser = userRepository.findByUsername(secondUsername)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + secondUsername));

        // Find the relationship between the two users
        UserRelationship relationship = userRelationshipRepository
                .findByFirstUsernameAndSecondUsername(firstUser, secondUser)
                .orElseThrow(() -> new IllegalArgumentException("Friendship not found"));

        // Check if the relationship type is "friend" before unfriending
        if (!"friend".equals(relationship.getRelationshipType())) {
            throw new IllegalArgumentException("You can only unfriend a user that you are friends with.");
        }

        // Delete the friendship relationship
        userRelationshipRepository.delete(relationship);
    }

}
