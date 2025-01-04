package com.victor.main.data.entity;

import androidx.annotation.NonNull;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/18
 * 邮箱：jxfengmtx@gmail.com
 */
public class EpcData {
    private String tid;
    private String rssi;
    private String epc;
    private int count;

    public EpcData(String tid, String rssi, String epc, int count) {
        this.tid = tid;
        this.rssi = rssi;
        this.epc = epc;
        this.count = count;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getRssi() {
        return rssi;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }

    public String getEpc() {
        return epc;
    }

    public void setEpc(String epc) {
        this.epc = epc;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @NonNull
    @Override
    public String toString() {
        return "rssi" + this.rssi + ",epc " + this.epc;
    }
}

