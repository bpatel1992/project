package com.rahul.project.gateway.dto;

/**
 * @author rahul malhotra
 * @Date 2019-05-28
 */

public class PostLikeDTO {

    private Long postId;
    private Long userId;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
