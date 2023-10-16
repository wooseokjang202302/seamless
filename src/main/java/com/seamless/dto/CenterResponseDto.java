package com.seamless.dto;

import com.seamless.entity.CenterEntity;

public class CenterResponseDto {
    private Long id;
    private String name;
    private String address;
    private String email;
    private String tel;
    private String homepage;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public CenterResponseDto(CenterEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.address = entity.getAddress();
        this.email = entity.getEmail();
        this.tel = entity.getTel();
        this.homepage = entity.getHomepage();
    }
}
