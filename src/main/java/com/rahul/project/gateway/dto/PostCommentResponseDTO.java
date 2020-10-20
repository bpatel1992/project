package com.rahul.project.gateway.dto;

import java.util.List;

/**
 * @author rahul malhotra
 * @Date 2019-05-25
 */
public class PostCommentResponseDTO {

    private Long postId;
    private List<PostCommentDTO> comments;
    private int pageNumber;
    private int pageSize;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public List<PostCommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<PostCommentDTO> comments) {
        this.comments = comments;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
