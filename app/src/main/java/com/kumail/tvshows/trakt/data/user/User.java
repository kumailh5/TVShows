package com.kumail.tvshows.trakt.data.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("private")
    @Expose
    private Boolean _private;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("vip")
    @Expose
    private Boolean vip;
    @SerializedName("vip_ep")
    @Expose
    private Boolean vipEp;
    @SerializedName("ids")
    @Expose
    private Ids ids;
    @SerializedName("joined_at")
    @Expose
    private String joinedAt;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("images")
    @Expose
    private Images images;
    @SerializedName("vip_og")
    @Expose
    private Boolean vipOg;
    @SerializedName("vip_years")
    @Expose
    private Integer vipYears;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getPrivate() {
        return _private;
    }

    public void setPrivate(Boolean _private) {
        this._private = _private;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getVip() {
        return vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }

    public Boolean getVipEp() {
        return vipEp;
    }

    public void setVipEp(Boolean vipEp) {
        this.vipEp = vipEp;
    }

    public Ids getIds() {
        return ids;
    }

    public void setIds(Ids ids) {
        this.ids = ids;
    }

    public String getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(String joinedAt) {
        this.joinedAt = joinedAt;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public Boolean getVipOg() {
        return vipOg;
    }

    public void setVipOg(Boolean vipOg) {
        this.vipOg = vipOg;
    }

    public Integer getVipYears() {
        return vipYears;
    }

    public void setVipYears(Integer vipYears) {
        this.vipYears = vipYears;
    }

}


