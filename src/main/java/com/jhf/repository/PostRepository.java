package com.jhf.repository;

import com.jhf.models.Post;
import com.jhf.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByUsername_Username(String username);
    List<Post> findByUsernameIn(List<User> users);
}
