package com.aihughes.peoplemon.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by alexhughes on 11/6/16.
 */

public class Account {


    @SerializedName("Email")
        private String email;

        @SerializedName("FullName")
        private String fullName;

        @SerializedName("AvatarBase64")
        private String avatarBase64;

        @SerializedName("ApiKey")
        private String apiKey;

        @SerializedName("Password")
        private String password;

        @SerializedName("HasRegistered")
        private Boolean hasRegistered;

        @SerializedName("LoginPrivider")
        private String loginProvided;

        @SerializedName("Id")
        private String id;

        @SerializedName("LastCheckInLongitude")
        private Integer lastLng;

        @SerializedName("LastCheckInLatitude")
        private Integer lastLat;

        @SerializedName("LastCheckInDateTime")
        private String lastCheckIn;

        @SerializedName("access_token")
        private String access_token;

        @SerializedName(".expires")
        private Date expires;


    public Account(String avatarBase64, String apiKey, String fullName, String email, String password) {
        this.email = email;
        this.fullName = fullName;
        this.avatarBase64 = avatarBase64;
        this.apiKey = apiKey;
        this.password = password;
    }
    public Account(){

    }

    public Account(String grantType, String email, String fullName) {
        this.email = email;
        this.fullName = fullName;
    }

    public Account(Integer lastLng, Integer lastLat) {
        this.lastLng = lastLng;
        this.lastLat = lastLat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatarBase64() {
        return avatarBase64;
    }

    public void setAvatarBase64(String avatarBase64) {
        this.avatarBase64 = avatarBase64;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getHasRegistered() {
        return hasRegistered;
    }

    public void setHasRegistered(Boolean hasRegistered) {
        this.hasRegistered = hasRegistered;
    }

    public String getLoginProvided() {
        return loginProvided;
    }

    public void setLoginProvided(String loginProvided) {
        this.loginProvided = loginProvided;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getLastLng() {
        return lastLng;
    }

    public void setLastLng(Integer lastLng) {
        this.lastLng = lastLng;
    }

    public Integer getLastLat() {
        return lastLat;
    }

    public void setLastLat(Integer lastLat) {
        this.lastLat = lastLat;
    }

    public String getLastCheckIn() {
        return lastCheckIn;
    }

    public void setLastCheckIn(String lastCheckIn) {
        this.lastCheckIn = lastCheckIn;
    }

    public String getToken() {return access_token;}

    public void setToken(String token) {this.access_token = token;}

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}