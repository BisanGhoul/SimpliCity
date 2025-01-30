package com.jhf.controller;

import com.jhf.dto.LikeDTO;
import com.jhf.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping
    public ResponseEntity<String> addLike(@RequestBody LikeDTO likeDTO) {
        likeService.addLike(likeDTO);
        return ResponseEntity.ok("Like added successfully");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteLike(@RequestBody LikeDTO likeDTO) {
        likeService.deleteLike(likeDTO);
        return ResponseEntity.ok("Like removed successfully");
    }
}
