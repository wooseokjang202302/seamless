package com.seamless.dto;

import jakarta.validation.constraints.NotNull;

public class BookmarkRequestDto {
    @NotNull
    private Long id;
    @NotNull(message = "로그인 상태를 확인해 주세요")
    private Long userId;
    @NotNull(message = "북마크할 대상을 찾지 못했습니다.")
    private Long postId;

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

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
