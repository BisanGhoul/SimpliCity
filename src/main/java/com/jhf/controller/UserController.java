package com.jhf.controller;

import com.jhf.dto.UserBasicInfoDTO;
import com.jhf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get all users

    @GetMapping
    public ResponseEntity<List<UserBasicInfoDTO>> getAllUsers() {
        List<UserBasicInfoDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Get user by username
    @GetMapping("/{username}")
    public ResponseEntity<UserBasicInfoDTO> getUserByUsername(@PathVariable String username) {
        Optional<UserBasicInfoDTO> user = userService.getUserByUsername(username);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create new user
    @PostMapping("/signup")
    public ResponseEntity<UserBasicInfoDTO> addUser(@RequestBody UserBasicInfoDTO user) {
        UserBasicInfoDTO createdUser = userService.addUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // Update user
    @PutMapping("/{username}")
    public ResponseEntity<UserBasicInfoDTO> updateUser(@PathVariable String username, @RequestBody UserBasicInfoDTO userDetails) {
        UserBasicInfoDTO updatedUser = userService.updateUser(username, userDetails);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // Delete user
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
