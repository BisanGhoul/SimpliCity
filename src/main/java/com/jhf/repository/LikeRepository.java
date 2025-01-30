package com.jhf.repository;

import com.jhf.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
    boolean existsByUsername_UsernameAndPost_Id (String username, Integer postId);

    void deleteByUsername_UsernameAndPost_Id(String username, Integer postId);

    List<Like> findByPost_Id(Integer postId);

    long countByPostId(Integer postId);
}

