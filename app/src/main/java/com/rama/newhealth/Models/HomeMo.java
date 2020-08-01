package com.rama.newhealth.Models;

public class HomeMo {
    private String itemImage;
    private String itemName;
    private String itemPrice;
    private String itemId;
    private String timestamp;
    private String itemDetail;
    private String itemQuantity;
    private String itemSubscriptionprice;
    private String itemDiscount;
    private String itemprevent;
    private String itembenefit;
    private String itemOneTime;

    public HomeMo() {
    }

    public HomeMo(String itemImage, String itemName, String itemPrice, String itemId, String timestamp, String itemDetail, String itemQuantity, String itemSubscriptionprice, String itemDiscount, String itemprevent, String itembenefit, String itemOneTime) {
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemId = itemId;
        this.timestamp = timestamp;
        this.itemDetail = itemDetail;
        this.itemQuantity = itemQuantity;
        this.itemSubscriptionprice = itemSubscriptionprice;
        this.itemDiscount = itemDiscount;
        this.itemprevent = itemprevent;
        this.itembenefit = itembenefit;
        this.itemOneTime = itemOneTime;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(String itemDetail) {
        this.itemDetail = itemDetail;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemSubscriptionprice() {
        return itemSubscriptionprice;
    }

    public void setItemSubscriptionprice(String itemSubscriptionprice) {
        this.itemSubscriptionprice = itemSubscriptionprice;
    }

    public String getItemDiscount() {
        return itemDiscount;
    }

    public void setItemDiscount(String itemDiscount) {
        this.itemDiscount = itemDiscount;
    }

    public String getItemprevent() {
        return itemprevent;
    }

    public void setItemprevent(String itemprevent) {
        this.itemprevent = itemprevent;
    }

    public String getItembenefit() {
        return itembenefit;
    }

    public void setItembenefit(String itembenefit) {
        this.itembenefit = itembenefit;
    }

    public String getItemOneTime() {
        return itemOneTime;
    }

    public void setItemOneTime(String itemOneTime) {
        this.itemOneTime = itemOneTime;
    }
}

