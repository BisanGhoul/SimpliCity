package com.jhf.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jhf.dto.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user", schema = "simplicity", uniqueConstraints = {
        @UniqueConstraint(name = "email", columnNames = {"email"})
})
public class User implements UserDetails {
    @Id
    @Size(max = 255)
    @Column(name = "username", nullable = false)
    private String username;

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Size(max = 15)
    @Column(name = "phone", length = 15)
    private String phone;

    @Size(max = 100)
    @NotNull
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Size(max = 100)
    @NotNull
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Size(max = 20)
    @Column(name = "gender", length = 20)
    private String gender;

    @Size(max = 500)
    @Column(name = "bio", length = 500)
    private String bio;

    @NotNull
//    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'USER'")
    @Column(name = "role", nullable = false)
    private Role role = Role.USER;

    @OneToMany(mappedBy = "username")
    private Set<Comment> comments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "groupOwnerUsername")
    private Set<Group> groups = new LinkedHashSet<>();

    @OneToMany(mappedBy = "username")
    private Set<Like> likes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "username")
    private Set<Post> posts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "username")
    private Set<UserGroupRelation> userGroupRelations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "firstUsername")
    private Set<UserRelationship> userRelationships = new LinkedHashSet<>();

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<UserGroupRelation> getUserGroupRelations() {
        return userGroupRelations;
    }

    public void setUserGroupRelations(Set<UserGroupRelation> userGroupRelations) {
        this.userGroupRelations = userGroupRelations;
    }

    public Set<UserRelationship> getUserRelationships() {
        return userRelationships;
    }

    public void setUserRelationships(Set<UserRelationship> userRelationships) {
        this.userRelationships = userRelationships;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + getRole().toString()));
    }

}