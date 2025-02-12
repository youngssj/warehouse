package com.victor.base.data.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2021/9/10
 * 邮箱：jxfengmtx@gmail.com
 */
@Entity
public class SyncInfo {
    @PrimaryKey
    private int syncId;
    @Ignore
    private int downValue;
    @Ignore
    private int upValue;
    @Ignore
    private int upTotalValue;
    @Ignore
    private int downTotalValue;
    private String syncText;
    private int syncStatus;
    private String syncDate;

    public SyncInfo() {

    }

    @Ignore
    public SyncInfo(int syncId, String syncText) {
        this.syncId = syncId;
        this.syncText = syncText;
    }

    public int getDownValue() {
        return downValue;
    }

    public void setDownValue(int downValue) {
        this.downValue = downValue;
    }

    public int getUpValue() {
        return upValue;
    }

    public void setUpValue(int upValue) {
        this.upValue = upValue;
    }

    public int getSyncId() {
        return syncId;
    }

    public void setSyncId(int syncId) {
        this.syncId = syncId;
    }

    public String getSyncText() {
        return syncText;
    }

    public void setSyncText(String syncText) {
        this.syncText = syncText;
    }

    public int getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(int syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(String syncDate) {
        this.syncDate = syncDate;
    }

    public int getUpTotalValue() {
        return upTotalValue;
    }

    public void setUpTotalValue(int upTotalValue) {
        this.upTotalValue = upTotalValue;
    }

    public int getDownTotalValue() {
        return downTotalValue;
    }

    public void setDownTotalValue(int downTotalValue) {
        this.downTotalValue = downTotalValue;
    }

    @Override
    public String toString() {
        return "SyncInfo{" +
                "syncId=" + syncId +
                ", downValue=" + downValue +
                ", upValue=" + upValue +
                ", upTotalValue=" + upTotalValue +
                ", downTotalValue=" + downTotalValue +
                ", syncText='" + syncText + '\'' +
                ", syncStatus=" + syncStatus +
                ", syncDate='" + syncDate + '\'' +
                '}';
    }
}
