package com.rahul.project.gateway.dto;

public class PartnerRequestDTO {

    private double lattitude;
    private double longitude;
    private int distance;
    private long partnerTypeId;
    private int pageNumber;
    private int pageSize;
    private String distanceUnit;

    public String getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(String distanceUnit) {
        this.distanceUnit = distanceUnit;
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

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public long getPartnerTypeId() {
        return partnerTypeId;
    }

    public void setPartnerTypeId(long partnerTypeId) {
        this.partnerTypeId = partnerTypeId;
    }
}
