package com.jhf.controller;

import com.jhf.service.UserRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-relationships")
public class UserRelationshipController {

    @Autowired
    private UserRelationshipService userRelationshipService;


    //TODO: handle edge cases (e.g. user doesnt exist, pending or friend)
    // Endpoint to send a friend request
    @PostMapping("/send-friend-request")
    public ResponseEntity<String> sendFriendRequest(@RequestParam String firstUsername, @RequestParam String secondUsername) {
        try {
            userRelationshipService.sendFriendRequest(firstUsername, secondUsername);
            return ResponseEntity.ok("Friend request sent.");
        } catch (IllegalArgumentException ex) {
            // Handle specific error cases and return an appropriate response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // Endpoint to accept a friend request
    @PostMapping("/accept-friend-request")
    public ResponseEntity<String> acceptFriendRequest(@RequestParam String firstUsername, @RequestParam String secondUsername) {
        userRelationshipService.acceptFriendRequest(firstUsername, secondUsername);
        return ResponseEntity.ok("Friend request accepted.");
    }


    // Endpoint to unfriend a user
    @PostMapping("/unfriend")
    public ResponseEntity<String> unfriend(@RequestParam String firstUsername, @RequestParam String secondUsername) {
        try {
            userRelationshipService.unfriend(firstUsername, secondUsername);
            return ResponseEntity.ok("User has been unfriended.");
        } catch (IllegalArgumentException ex) {
            // Handle specific error cases and return an appropriate response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
