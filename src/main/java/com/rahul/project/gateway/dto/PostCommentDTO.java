package com.rahul.project.gateway.dto;

/**
 * @author rahul malhotra
 * @Date 2019-05-25
 */
public class PostCommentDTO {

    private Long id;
    private String comment;
    private String creationDate;
    private String modificationDate;

    private PostDto post;
    private UserRelationDTO commentUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }

    public PostDto getPost() {
        return post;
    }

    public void setPost(PostDto post) {
        this.post = post;
    }

    public UserRelationDTO getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(UserRelationDTO commentUser) {
        this.commentUser = commentUser;
    }
}
