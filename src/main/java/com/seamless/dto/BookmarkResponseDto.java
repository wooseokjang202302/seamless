package com.seamless.dto;

public class BookmarkResponseDto {
    private Long centerId;
    private String centerName;
    private String centerAddress;
    private String centerEmail;
    private String centerTel;
    private String centerHomepage;
    private Double centerMapx;
    private Double centerMapy;

    public Long getCenterId() {
        return centerId;
    }

    public void setCenterId(Long centerId) {
        this.centerId = centerId;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getCenterAddress() {
        return centerAddress;
    }

    public void setCenterAddress(String centerAddress) {
        this.centerAddress = centerAddress;
    }

    public String getCenterEmail() {
        return centerEmail;
    }

    public void setCenterEmail(String centerEmail) {
        this.centerEmail = centerEmail;
    }

    public String getCenterTel() {
        return centerTel;
    }

    public void setCenterTel(String centerTel) {
        this.centerTel = centerTel;
    }

    public String getCenterHomepage() {
        return centerHomepage;
    }

    public void setCenterHomepage(String centerHomepage) {
        this.centerHomepage = centerHomepage;
    }

    public Double getCenterMapx() {
        return centerMapx;
    }

    public void setCenterMapx(Double centerMapx) {
        this.centerMapx = centerMapx;
    }

    public Double getCenterMapy() {
        return centerMapy;
    }

    public void setCenterMapy(Double centerMapy) {
        this.centerMapy = centerMapy;
    }
}
