package com.rahul.project.gateway.dto.services;

import com.rahul.project.gateway.dto.PartnerServiceDTO;

import java.util.List;

public class PartnerServiceListDTO {

    private List<PartnerServiceDTO> partnerServiceDTOList;

    public List<PartnerServiceDTO> getPartnerServiceDTOList() {
        return partnerServiceDTOList;
    }

    public void setPartnerServiceDTOList(List<PartnerServiceDTO> partnerServiceDTOList) {
        this.partnerServiceDTOList = partnerServiceDTOList;
    }
}
