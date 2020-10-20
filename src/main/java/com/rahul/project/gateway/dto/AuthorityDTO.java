package com.rahul.project.gateway.dto;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
public class AuthorityDTO {

    private Long authorityId;

    private String name;

    public Long getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
