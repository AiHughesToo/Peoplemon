package com.aihughes.peoplemon.Models;


import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by alexhughes on 11/6/16.
 */

public class User {


    @SerializedName("UserId")
    private String id;

    @SerializedName("Latitude")
    private Double lat;

    @SerializedName("Longitude")
    private Double lng;

    @SerializedName("AvatarBase64")
    private String avatarBase64;

    @SerializedName("UserName")
    private String userName;

    @SerializedName("Created")
    private String created;

    @SerializedName("Radius")
    private Integer radius;

    @SerializedName("CaughtUserId")
    private String caughtUserId;

    @SerializedName ("RadiusInMeters")
    private float radiusInMeters;



    public User(String id, String userName, String avatarBase64, double lat, double lng, String created) {
        this.id = id;
        this.userName = userName;
        this.avatarBase64 = avatarBase64;
        this.lat = lat;
        this.lng = lng;
        this.created = created;

    }

    public String getCaughtUserId() {
        return caughtUserId;
    }

    public void setCaughtUserId(String caughtUserId) {
        this.caughtUserId = caughtUserId;
    }

    public float getRadiusInMeters() {
        return radiusInMeters;
    }

    public void setRadiusInMeters(Integer radiusInMeters) {
        this.radiusInMeters = radiusInMeters;
    }

    public User(String caughtUserId, float radiusInMeters) {
        this.caughtUserId = caughtUserId;
        this.radiusInMeters = radiusInMeters;
    }

    public User(Integer radius) {
        this.radius = radius;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getAvatarBase64() {
        return avatarBase64;
    }

    public void setAvatarBase64(String avatarBase64) {
        this.avatarBase64 = avatarBase64;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }
}
