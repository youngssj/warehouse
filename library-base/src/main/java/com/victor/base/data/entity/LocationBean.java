package com.victor.base.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class LocationBean implements Parcelable {
    private String whAreaName;
    private String whLocationName;
    private String warehouseId;
    private String whLocationId;
    private String warehouseName;
    private String whAreaId;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.whAreaName);
        dest.writeString(this.whLocationName);
        dest.writeString(this.warehouseId);
        dest.writeString(this.whLocationId);
        dest.writeString(this.warehouseName);
        dest.writeString(this.whAreaId);
    }

    public LocationBean() {
    }

    protected LocationBean(Parcel in) {
        this.whAreaName = in.readString();
        this.whLocationName = in.readString();
        this.warehouseId = in.readString();
        this.whLocationId = in.readString();
        this.warehouseName = in.readString();
        this.whAreaId = in.readString();
    }

    public static final Parcelable.Creator<LocationBean> CREATOR = new Parcelable.Creator<LocationBean>() {
        @Override
        public LocationBean createFromParcel(Parcel source) {
            return new LocationBean(source);
        }

        @Override
        public LocationBean[] newArray(int size) {
            return new LocationBean[size];
        }
    };

    public String getWhAreaName() {
        return whAreaName;
    }

    public void setWhAreaName(String whAreaName) {
        this.whAreaName = whAreaName;
    }

    public String getWhLocationName() {
        return whLocationName;
    }

    public void setWhLocationName(String whLocationName) {
        this.whLocationName = whLocationName;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWhLocationId() {
        return whLocationId;
    }

    public void setWhLocationId(String whLocationId) {
        this.whLocationId = whLocationId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWhAreaId() {
        return whAreaId;
    }

    public void setWhAreaId(String whAreaId) {
        this.whAreaId = whAreaId;
    }
}
