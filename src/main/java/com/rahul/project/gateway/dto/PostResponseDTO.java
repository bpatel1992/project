package com.rahul.project.gateway.dto;

import java.util.List;

/**
 * @author rahul malhotra
 * @Date 2019-05-24
 */
public class PostResponseDTO {

    private List<PostDto> posts;
    private int pageNumber;
    private int pageSize;

    public List<PostDto> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDto> posts) {
        this.posts = posts;
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
