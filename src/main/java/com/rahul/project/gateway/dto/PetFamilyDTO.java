package com.rahul.project.gateway.dto;

/**
 * @author rahul malhotra
 * @Date 2019-04-30
 */
public class PetFamilyDTO {

    private Long id;
    private String name;
    private String pfDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPfDesc() {
        return pfDesc;
    }

    public void setPfDesc(String pfDesc) {
        this.pfDesc = pfDesc;
    }
}
