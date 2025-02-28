package com.victor.base.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class OperateCategory implements Parcelable {

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

    public OperateCategory() {
    }

    public OperateCategory(String categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    protected OperateCategory(Parcel in) {
        this.categoryId = in.readString();
        this.categoryName = in.readString();
    }

    public static final Parcelable.Creator<OperateCategory> CREATOR = new Parcelable.Creator<OperateCategory>() {
        @Override
        public OperateCategory createFromParcel(Parcel source) {
            return new OperateCategory(source);
        }

        @Override
        public OperateCategory[] newArray(int size) {
            return new OperateCategory[size];
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
