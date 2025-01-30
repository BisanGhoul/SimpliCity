package com.jhf.repository;

import com.jhf.models.User;
import com.jhf.models.UserRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRelationshipRepository extends JpaRepository<UserRelationship, Integer> {
    Optional<UserRelationship> findByFirstUsernameAndSecondUsername(User firstUsername, User secondUsername);

    Optional<UserRelationship> findBySecondUsernameAndFirstUsername(User firstUsername, User secondUsername);

    List<UserRelationship> findByFirstUsernameOrSecondUsername(User user, User user1);
}
