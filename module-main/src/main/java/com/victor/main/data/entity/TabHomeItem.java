package com.victor.main.data.entity;

import android.graphics.drawable.Drawable;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/15
 * 邮箱：jxfengmtx@gmail.com
 */
public class TabHomeItem {
    private int id;
    private String title;
    private Drawable imgRes;
    private String breakPage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getImgRes() {
        return imgRes;
    }

    public void setImgRes(Drawable imgRes) {
        this.imgRes = imgRes;
    }
}
