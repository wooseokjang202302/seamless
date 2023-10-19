package com.seamless.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bookmarks")
public class BookmarkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long centerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCenterId() {
        return centerId;
    }

    public void setCenterId(Long postId) {
        this.centerId = postId;
    }
}