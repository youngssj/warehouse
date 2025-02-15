package com.example.hiu_931;


import java.util.HashMap;
import java.util.Map;

/**********************************
 * 标签
 *
 *
 * Created by tutu on 2022-09-07
 **********************************/
public class Tag {
    private String epc;
    private String tid;
    private int count;

    public String getEpc() {
        return epc;
    }

    public void setEpc(String epc) {
        this.epc = epc;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("epc", epc);
        map.put("tid", tid);
        map.put("count", count);
        return map;
    }
}