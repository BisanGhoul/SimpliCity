package com.jhf;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "`group`", schema = "simplicity", indexes = {
        @Index(name = "idx_user_group_owner", columnList = "group_owner_username")
})
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "group_name", nullable = false)
    private String groupName;

    @Size(max = 1000)
    @Column(name = "group_description", length = 1000)
    private String groupDescription;

    @Size(max = 255)
    @Column(name = "group_pic_url")
    private String groupPicUrl;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "group_owner_username", nullable = false)
    private com.jhf.User groupOwnerUsername;

    @Size(max = 50)
    @Column(name = "status", length = 50)
    private String status;

    @OneToMany(mappedBy = "group")
    private Set<com.jhf.Post> posts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "group")
    private Set<com.jhf.UserGroupRelation> userGroupRelations = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public String getGroupPicUrl() {
        return groupPicUrl;
    }

    public void setGroupPicUrl(String groupPicUrl) {
        this.groupPicUrl = groupPicUrl;
    }

    public com.jhf.User getGroupOwnerUsername() {
        return groupOwnerUsername;
    }

    public void setGroupOwnerUsername(com.jhf.User groupOwnerUsername) {
        this.groupOwnerUsername = groupOwnerUsername;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<com.jhf.Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<com.jhf.Post> posts) {
        this.posts = posts;
    }

    public Set<com.jhf.UserGroupRelation> getUserGroupRelations() {
        return userGroupRelations;
    }

    public void setUserGroupRelations(Set<com.jhf.UserGroupRelation> userGroupRelations) {
        this.userGroupRelations = userGroupRelations;
    }

}