package com.victor.base.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class InboundCategory implements Parcelable {

    private String categoryId;
    private String categoryName;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.categoryId);
        dest.writeString(this.categoryName);
    }

    public InboundCategory() {
    }

    public InboundCategory(String categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    protected InboundCategory(Parcel in) {
        this.categoryId = in.readString();
        this.categoryName = in.readString();
    }

    public static final Parcelable.Creator<InboundCategory> CREATOR = new Parcelable.Creator<InboundCategory>() {
        @Override
        public InboundCategory createFromParcel(Parcel source) {
            return new InboundCategory(source);
        }

        @Override
        public InboundCategory[] newArray(int size) {
            return new InboundCategory[size];
        }
    };

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
