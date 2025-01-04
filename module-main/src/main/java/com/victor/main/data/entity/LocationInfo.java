package com.victor.main.data.entity;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2021/1/22
 * 邮箱：jxfengmtx@gmail.com
 */
public class LocationInfo {
    private String rfidcode;
    private String latitude;
    private String longitude;

    public LocationInfo(String rfidcode, String latitude, String longitude) {
        this.rfidcode = rfidcode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getRfidcode() {
        return rfidcode;
    }

    public void setRfidcode(String rfidcode) {
        this.rfidcode = rfidcode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
