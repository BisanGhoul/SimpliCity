package com.jhf.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "user_relationship", schema = "simplicity", indexes = {
        @Index(name = "idx_user_relationship_first", columnList = "first_username"),
        @Index(name = "idx_user_relationship_second", columnList = "second_username")
})
public class UserRelationship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relationship_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "first_username", nullable = false)
    private User firstUsername;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "second_username", nullable = false)
    private User secondUsername;

    @Size(max = 50)
    @Column(name = "relationship_type", length = 50)
    private String relationshipType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getFirstUsername() {
        return firstUsername;
    }

    public void setFirstUsername(User firstUsername) {
        this.firstUsername = firstUsername;
    }

    public User getSecondUsername() {
        return secondUsername;
    }

    public void setSecondUsername(User secondUsername) {
        this.secondUsername = secondUsername;
    }

    public String getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }

}