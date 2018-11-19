package com.yovanydev.appproductos.products.domain.model;

import java.util.Locale;
import java.util.UUID;

public class Producto {

    private String mCode;
    private String mName;
    private String mDescription;
    private String mBrand;
    private float mPrice;
    private int mUnitsInStock;
    private String mImageUrl;

    public Producto(String mName, float mPrice, String mImageUrl) {
        this.mCode = UUID.randomUUID().toString();
        this.mName = mName;
        this.mPrice = mPrice;
        this.mImageUrl = mImageUrl;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmBrand() {
        return mBrand;
    }

    public void setmBrand(String mBrand) {
        this.mBrand = mBrand;
    }

    public float getmPrice() {
        return mPrice;
    }

    public void setmPrice(float mPrice) {
        this.mPrice = mPrice;
    }

    public int getmUnitsInStock() {
        return mUnitsInStock;
    }

    public void setmUnitsInStock(int mUnitsInStock) {
        this.mUnitsInStock = mUnitsInStock;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getFormatedPrice() {
        return String.format("$%s", mPrice);
    }

    public String getFormatedUnitsInStock() {
        return String.format(Locale.getDefault(), "%d u.", mUnitsInStock);
    }
}
