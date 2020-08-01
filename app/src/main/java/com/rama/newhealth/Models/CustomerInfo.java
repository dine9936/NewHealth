package com.rama.newhealth.Models;

public class CustomerInfo {
    private String userName;
    private String userRegion;
    private String userLocation;
    private String userImage;
    private String userPhone;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRegion() {
        return userRegion;
    }

    public void setUserRegion(String userRegion) {
        this.userRegion = userRegion;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public CustomerInfo() {
    }

    public CustomerInfo(String userName, String userRegion, String userLocation, String userImage, String userPhone) {
        this.userName = userName;
        this.userRegion = userRegion;
        this.userLocation = userLocation;
        this.userImage = userImage;
        this.userPhone = userPhone;
    }
}
