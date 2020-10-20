package com.rahul.project.gateway.dto;

/**
 * @author rahul malhotra
 * @Date 2019-05-26
 */
public class UserRelationDTO {

    private Long id;
    private String userName;
    private String profilePicURL;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfilePicURL() {
        return profilePicURL;
    }

    public void setProfilePicURL(String profilePicURL) {
        this.profilePicURL = profilePicURL;
    }
}
