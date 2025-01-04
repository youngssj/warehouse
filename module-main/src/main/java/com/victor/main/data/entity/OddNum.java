package com.victor.main.data.entity;

import android.graphics.drawable.Drawable;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/22
 * 邮箱：jxfengmtx@gmail.com
 */
public class OddNum {

    /**
     * num : 1
     * title : 待盘点单
     */
    private int flagId;
    private int num;
    private Drawable leftImg;
    private String title;


    public int getFlagId() {
        return flagId;
    }

    public void setFlagId(int flagId) {
        this.flagId = flagId;
    }


    public Drawable getLeftImg() {
        return leftImg;
    }

    public void setLeftImg(Drawable leftImg) {
        this.leftImg = leftImg;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
