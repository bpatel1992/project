package com.rahul.project.gateway.dto;

import java.math.BigDecimal;

public class PartnerServiceResponseDTO {


    private PartnerDTO partnerId;
    private ServiceDTO serviceId;
    private BigDecimal discount;
    private BigDecimal offeredPrice;
    private BigDecimal finalPrice;

    public PartnerDTO getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(PartnerDTO partnerId) {
        this.partnerId = partnerId;
    }

    public ServiceDTO getServiceId() {
        return serviceId;
    }

    public void setServiceId(ServiceDTO serviceId) {
        this.serviceId = serviceId;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getOfferedPrice() {
        return offeredPrice;
    }

    public void setOfferedPrice(BigDecimal offeredPrice) {
        this.offeredPrice = offeredPrice;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }
}
