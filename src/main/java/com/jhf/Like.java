package com.jhf;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
@Table(name = "`like`", schema = "simplicity", indexes = {
        @Index(name = "idx_like_user", columnList = "username"),
        @Index(name = "idx_like_post", columnList = "post_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "username", columnNames = {"username", "post_id"})
})
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id", nullable = false)
    private Integer id;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "username", nullable = false)
    private com.jhf.User username;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "post_id", nullable = false)
    private com.jhf.Post post;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public com.jhf.User getUsername() {
        return username;
    }

    public void setUsername(com.jhf.User username) {
        this.username = username;
    }

    public com.jhf.Post getPost() {
        return post;
    }

    public void setPost(com.jhf.Post post) {
        this.post = post;
    }

}