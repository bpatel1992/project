package com.rahul.project.gateway.dto;

import java.util.ArrayList;
import java.util.List;

public class PartnerResponseDTO {

    private String partnerName;
    private Long id;
    private String address;
    private double lattitude;
    private double longitude;
    private Float partnerExperience;
    private String partnerDesc;
    private String mobile;
    private String email;
    private Long addressId;
    private Double distance;
    private boolean isClinicOpen;
    private List<String> timeRange;

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Float getPartnerExperience() {
        return partnerExperience;
    }

    public void setPartnerExperience(Float partnerExperience) {
        this.partnerExperience = partnerExperience;
    }

    public String getPartnerDesc() {
        return partnerDesc;
    }

    public void setPartnerDesc(String partnerDesc) {
        this.partnerDesc = partnerDesc;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(List<String> timeRange) {
        this.timeRange = timeRange;
    }

    public boolean isClinicOpen() {
        return isClinicOpen;
    }

    public void setClinicOpen(boolean clinicOpen) {
        isClinicOpen = clinicOpen;
    }

}
