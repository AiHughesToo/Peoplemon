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

        @SerializedName("LoginProvider")
        private String loginProvider;

        @SerializedName("Id")
        private String id;

        @SerializedName("LastCheckInLongitude")
        private Double lastLng;

        @SerializedName("LastCheckInLatitude")
        private Double lastLat;

        @SerializedName("LastCheckInDateTime")
        private String lastCheckIn;

        @SerializedName("access_token")
        private String access_token;

        @SerializedName(".expires")
        private Date expires;

        @SerializedName("Latitude")
        private double lat;

         @SerializedName("Longitude")
         private double lng;

    public Account(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Account(String ID, String Email, Boolean HasRegistered, String LoginProvider, String FullName, String AvatarBase64, Double LastCheckInLongitude, Double LastCheckInLatitude, String LastCheckInDateTime) {
        this.id = ID;
        this.email = Email;
        this.hasRegistered = HasRegistered;
        this.loginProvider = LoginProvider;
        this.fullName = FullName;
        this.avatarBase64 = AvatarBase64;
        this.lastLng = LastCheckInLongitude;
        this.lastLat = LastCheckInLatitude;
        this.lastCheckIn = LastCheckInDateTime;
    }

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


    public Account(String avatarBase64, String fullName) {
        this.avatarBase64 = avatarBase64;
        this.fullName = fullName;
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
        return loginProvider;
    }

    public void setLoginProvided(String loginProvided) {
        this.loginProvider = loginProvided;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLastLng() {
        return lastLng;
    }

    public void setLastLng(Double lastLng) {
        this.lastLng = lastLng;
    }

    public Double getLastLat() {
        return lastLat;
    }

    public void setLastLat(Double lastLat) {
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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}