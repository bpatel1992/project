package com.rahul.project.gateway.dto;

public class PartnerTypeDTO {
    private Long id;
    private String name;
    private String partnerTypeDesc;
    private boolean status;

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

    public String getPartnerTypeDesc() {
        return partnerTypeDesc;
    }

    public void setPartnerTypeDesc(String partnerTypeDesc) {
        this.partnerTypeDesc = partnerTypeDesc;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
