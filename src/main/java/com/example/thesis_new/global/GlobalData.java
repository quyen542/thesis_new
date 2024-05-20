package com.example.thesis_new.global;

import com.example.thesis_new.dto.CheckOutInfor;

public class GlobalData {

    public static String CurrentCategory = null;

    public static String Currentkeyword = null;

    public static String CurrentRatingTime = "day";

    public static CheckOutInfor checkOutInforTemp = null;

    public static Long cartIdTemp = null;

    public static String phonenumberTemp = null;

    public String getCurrentCategory() {
        return CurrentCategory;
    }

    public void setCurrentCategory(String currentCategory) {
        CurrentCategory = currentCategory;
    }

    public String getCurrentkeyword() {
        return Currentkeyword;
    }

    public void setCurrentkeyword(String currentkeyword) {
        Currentkeyword = currentkeyword;
    }

    public String getCurrentRatingTime() {
        return CurrentRatingTime;
    }

    public void setCurrentRatingTime(String currentRatingTime) {
        CurrentRatingTime = currentRatingTime;
    }

    public CheckOutInfor getCheckOutInforTemp() {
        return checkOutInforTemp;
    }

    public void setCheckOutInforTemp(CheckOutInfor checkOutInforTemp) {
        this.checkOutInforTemp = checkOutInforTemp;
    }

    public Long getCartIdTemp() {
        return cartIdTemp;
    }

    public void setCartIdTemp(Long cartIdTemp) {
        this.cartIdTemp = cartIdTemp;
    }

    public String getPhonenumberTemp() {
        return phonenumberTemp;
    }

    public void setPhonenumberTemp(String phonenumberTemp) {
        this.phonenumberTemp = phonenumberTemp;
    }
}
