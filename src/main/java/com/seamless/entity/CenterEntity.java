package com.seamless.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "centers")
public class CenterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String email;
    private String tel;
    private String homepage;
    private Double mapx;
    private Double mapy;
    private String do_si;
    private String si_gun_gu;
    private String dong;
    private String area_etc;

    public Double getMapx() {
        return mapx;
    }

    public void setMapx(Double mapx) {
        this.mapx = mapx;
    }

    public Double getMapy() {
        return mapy;
    }

    public void setMapy(Double mapy) {
        this.mapy = mapy;
    }

    public String getDo_si() {
        return do_si;
    }

    public void setDo_si(String do_si) {
        this.do_si = do_si;
    }

    public String getSi_gun_gu() {
        return si_gun_gu;
    }

    public void setSi_gun_gu(String si_gun_gu) {
        this.si_gun_gu = si_gun_gu;
    }

    public String getDong() {
        return dong;
    }

    public void setDong(String dong) {
        this.dong = dong;
    }

    public String getArea_etc() {
        return area_etc;
    }

    public void setArea_etc(String area_etc) {
        this.area_etc = area_etc;
    }

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
}
