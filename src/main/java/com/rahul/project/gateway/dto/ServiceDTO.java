package com.rahul.project.gateway.dto;

import javax.validation.constraints.NotNull;

public class ServiceDTO {

    private Long id;

    @NotNull(message = "Validator.notNullMsg")
    private String serviceName;
    @NotNull(message = "Validator.notNullMsg")
    private String serviceDesc;

    private boolean status;

    private ServiceDTO parentServiceId;

    private ServiceTypeDTO serviceTypeId;

    public ServiceTypeDTO getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(ServiceTypeDTO serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ServiceDTO getParentServiceId() {
        return parentServiceId;
    }

    public void setParentServiceId(ServiceDTO parentServiceId) {
        this.parentServiceId = parentServiceId;
    }
}
